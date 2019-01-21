package com.ujazdowski.client.utils;

import com.ujazdowski.client.service.dto.MessageDto;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

public class MessageSortedList extends AbstractList<MessageDto> {
    private ArrayList<MessageDto> arrayList = new ArrayList();

    @Override
    public MessageDto get(int index) {
        return arrayList.get(index);
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public boolean add(MessageDto messageDto) {
        boolean result = arrayList.add(messageDto);
        if (result) {
            Collections.sort(arrayList, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        }
        return result;
    }
}
