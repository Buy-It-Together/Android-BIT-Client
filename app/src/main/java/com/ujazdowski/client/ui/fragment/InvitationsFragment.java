package com.ujazdowski.client.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;
import com.ujazdowski.client.service.ChatService;
import com.ujazdowski.client.service.ErrorService;
import com.ujazdowski.client.service.dto.ChatDto;
import com.ujazdowski.client.service.geocoder.GeocoderAbstractService;
import com.ujazdowski.client.ui.adapter.ChatAdapter;
import dagger.android.support.DaggerFragment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvitationsFragment extends DaggerFragment implements Title {
    @Inject
    ErrorService errorService;
    @Inject
    GeocoderAbstractService mapService;
    @Inject
    ChatService chatService;
    @BindView(R.id.fragment_invitation_recycle_view)
    RecyclerView recyclerView;
    private List<ChatDto> chatDtoList = new ArrayList<>();
    private ViewGroup container;

    public static InvitationsFragment newInstance() {
        InvitationsFragment fragment = new InvitationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void add(ChatDto chatDto) {
        chatDtoList.add(chatDto);
    }

    public void add(Collection<ChatDto> set) {
        chatDtoList.addAll(set);
    }

    public void setChats(List chats) {
        chatDtoList = chats;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitations, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ChatAdapter(chatService, errorService, getActivity(), chatDtoList));
        this.container = container;
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Communicate)) {
            throw new RuntimeException(context.toString() + " must implement Communicate");
        }
    }

    @Override
    public int getTitleResource() {
        return R.string.invitations_page_title;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public interface Communicate {
        void accepted(Long chatId, Long userOfferChatId);
    }
}
