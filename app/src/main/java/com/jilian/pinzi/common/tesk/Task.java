package com.jilian.pinzi.common.tesk;

import android.graphics.Bitmap;

import com.jilian.pinzi.common.dto.FriendCircleListDto;
import com.jilian.pinzi.utils.BitmapUtils;
import com.jilian.pinzi.utils.EmptyUtils;

import java.util.List;
import java.util.concurrent.Callable;

public class Task implements Callable<List<FriendCircleListDto>> {
    private List<FriendCircleListDto> list;

    public Task(List<FriendCircleListDto> list) {
        this.list = list;
    }
    @Override
    public List<FriendCircleListDto>  call() throws Exception {
        //对视频封面处理 耗时操作
        for (int i = 0; i < list.size(); i++) {
            //对视频封面处理 耗时操作
            //视频地址不为空
            //具体操作
            if (EmptyUtils.isNotEmpty(list.get(i)) && EmptyUtils.isNotEmpty(list.get(i).getVideo())) {
                list.get(i).setBitmap(BitmapUtils.getNetVideoBitmap(list.get(i).getVideo()));
                return list;
            }
        }
        return list;

    }
}
