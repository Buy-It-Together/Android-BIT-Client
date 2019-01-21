package com.ujazdowski.client.ui.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ujazdowski.client.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_message_email)
    TextView author;
    @BindView(R.id.item_message_content)
    TextView content;
    @BindView(R.id.item_message_date)
    TextView date;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public String getAuthor() {
        return author.getText().toString();
    }

    public void setAuthor(String author) {
        this.author.setText(author);
    }

    public String getContent() {
        return content.getText().toString();
    }

    public void setContent(String content) {
        this.content.setText(content);
    }

    public String getDate() {
        return date.getText().toString();
    }

    public void setDate(String date) {
        this.date.setText(date);
    }
}