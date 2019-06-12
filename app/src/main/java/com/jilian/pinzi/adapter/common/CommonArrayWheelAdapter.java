package com.jilian.pinzi.adapter.common;

import com.contrarywind.adapter.WheelAdapter;
import com.jilian.pinzi.common.dto.DeliveryMethodDto;

import java.util.List;

public class CommonArrayWheelAdapter implements WheelAdapter {
    // items
    private List<DeliveryMethodDto> items;

    /**
     * Constructor
     *
     * @param items the items
     */
    public CommonArrayWheelAdapter(List<DeliveryMethodDto> items) {
        this.items = items;

    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index).getDeliveryMethodName();
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
