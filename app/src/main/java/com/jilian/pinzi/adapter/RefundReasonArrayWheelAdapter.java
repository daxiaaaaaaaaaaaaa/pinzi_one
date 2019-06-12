package com.jilian.pinzi.adapter;

import com.contrarywind.adapter.WheelAdapter;
import com.jilian.pinzi.common.dto.DeliveryMethodDto;
import com.jilian.pinzi.common.dto.RefundReasonDto;

import java.util.List;

public class RefundReasonArrayWheelAdapter implements WheelAdapter {
    // items
    private List<RefundReasonDto> items;

    /**
     * Constructor
     *
     * @param items the items
     */
    public RefundReasonArrayWheelAdapter(List<RefundReasonDto> items) {
        this.items = items;

    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index).getContent();
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }

}
