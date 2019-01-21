package com.ujazdowski.client.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;
import com.ujazdowski.client.ui.fragment.ChatFragment;
import com.ujazdowski.client.ui.fragment.InvitationsFragment;
import com.ujazdowski.client.ui.fragment.OfferDetailsUpdateFragment;
import com.ujazdowski.client.service.ChatService;
import com.ujazdowski.client.service.dto.ChatDto;
import com.ujazdowski.client.service.dto.ExtraOfferDto;
import com.ujazdowski.client.service.dto.UserOfferChatDto;
import com.ujazdowski.client.utils.ScreenSlidePagerAdapter;
import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OfferActivity extends DaggerAppCompatActivity implements InvitationsFragment.Communicate {

    @Inject
    ChatService chatService;
    @BindView(R.id.viewPager_offer_tabs)
    ViewPager viewPager;
    @BindView(R.id.activity_offer_tab_layout)
    TabLayout tabLayout;

    private ScreenSlidePagerAdapter pagerAdapter;
    private InvitationsFragment invitationsFragment;
    private OfferDetailsUpdateFragment offerDetailsUpdateFragment;
    private ChatFragment chatFragment;
    private List<ChatDto> chatDtoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExtraOfferDto offerDto = (ExtraOfferDto) getIntent().getSerializableExtra(ExtraOfferDto.class.getSimpleName());
        setContentView(R.layout.activity_offer);
        ButterKnife.bind(this);

        ChatCallback chatCallback = new ChatCallback();
        chatService.getChatsByOfferId(chatCallback, offerDto.getId());
        invitationsFragment = InvitationsFragment.newInstance();
        chatFragment = ChatFragment.newInstance();
        offerDetailsUpdateFragment = OfferDetailsUpdateFragment.newInstance(offerDto);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(offerDetailsUpdateFragment);
        pagerAdapter = new ScreenSlidePagerAdapter(this, getSupportFragmentManager(), fragments);
        setFragments();
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void accepted(Long chatId, Long userOfferChatId) {
        UserOfferChatDto userOfferChat = findUserOfferChat(chatId, userOfferChatId);
        userOfferChat.setAccepted(true);
        setFragments();
    }

    private UserOfferChatDto findUserOfferChat(Long chatId, Long userOfferChatId) {
        for (ChatDto chat : chatDtoList) {
            if (chat.getId().equals(chatId)) {
                for (UserOfferChatDto offerChatDto : chat.getUserOfferChatVMs()) {
                    if (offerChatDto.getId().equals(userOfferChatId)) {
                        return offerChatDto;
                    }
                }
            }
        }
        return null;
    }

    private void toast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private Boolean isChatAccepted() {
        for (ChatDto chat : chatDtoList) {
            for (UserOfferChatDto offerChatDto : chat.getUserOfferChatVMs()) {
                if (offerChatDto.getMy()) {
                    return offerChatDto.getAccepted();
                }
            }
        }
        return false;
    }

    /**
     * Funkcja decyduje, który fragment powinien być wyświetlany Chat/Invitation Fragment
     */
    private void setFragments() {
        viewPager.setCurrentItem(0);
        if (chatDtoList.size() == 0) {
            pagerAdapter.remove(invitationsFragment);
            pagerAdapter.remove(chatFragment);
            offerDetailsUpdateFragment.setEditable(true);
        } else if (isChatAccepted()) {
            pagerAdapter.remove(invitationsFragment);
            pagerAdapter.add(chatFragment);
            chatFragment.setChat(getAcceptedChat());
            offerDetailsUpdateFragment.setEditable(false);
        } else {
            pagerAdapter.remove(chatFragment);
            pagerAdapter.add(invitationsFragment);
            offerDetailsUpdateFragment.setEditable(true);
        }
        pagerAdapter.notifyDataSetChanged();
    }

    private ChatDto getAcceptedChat() {
        for (ChatDto chatDto : chatDtoList) {
            for (UserOfferChatDto userOfferChatVM : chatDto.getUserOfferChatVMs()) {
                if (userOfferChatVM.getMy() && userOfferChatVM.getAccepted()) {
                    return chatDto;
                }
            }
        }
        return null;
    }

    private class ChatCallback implements Callback<Set<ChatDto>> {

        @Override
        public void onResponse(Call<Set<ChatDto>> call, Response<Set<ChatDto>> response) {
            if (response.isSuccessful()) {
                chatDtoList.addAll(response.body());
                invitationsFragment.setChats(chatDtoList);
                setFragments();
                invitationsFragment.onResume();
            } else {
                toast(R.string.error_offer_can_not_send_data);
            }
        }

        @Override
        public void onFailure(Call<Set<ChatDto>> call, Throwable t) {
            toast(R.string.error_offer_can_not_send_data);
        }
    }
}
