/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hzx.androidtransitionanimationdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建transtions的帮助类，使用了{@link android.app.ActivityOptions}.
 */
public class TransitionHelper {

    private TransitionHelper() {}

    /**
     * 创建Active Transition 过渡动画，避免与系统UI问题。
     *
     * @param activity 用作启动transtion动画的activity
     * @param includeStatusBar 如果是 false, status bar 就不添加 transition 过渡动画
     * @return 所有添加了 transition 过渡动画的View的List.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Pair<View, String>[] createSafeTransitionParticipants(@NonNull Activity activity,
                                                          boolean includeStatusBar,
                                                          @Nullable Pair... otherParticipants) {
        // 1，这里避免系统UI绘制问题:
        View decor = activity.getWindow().getDecorView();
        View statusBar = null;
        if (includeStatusBar) {
            statusBar = decor.findViewById(android.R.id.statusBarBackground);
        }
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);

        // 2，创建用于存储 transition 的pair集合.
        List<Pair> participants = new ArrayList<>(3);
        addNonNullViewToTransitionParticipants(statusBar, participants);
        addNonNullViewToTransitionParticipants(navBar, participants);

        // 3，只有otherParticipants不为空且至少有一个，才添加进participants。
        if (otherParticipants != null && !(otherParticipants.length == 1
                && otherParticipants[0] == null)) {
            participants.addAll(Arrays.asList(otherParticipants));
        }

        // 4，返回添加了translation的数组
        return participants.toArray(new Pair[participants.size()]);
    }

    /**
     * 检查View不为空
     * @param view 要添加transaction的View
     * @param participants 存储transition的List
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void addNonNullViewToTransitionParticipants(View view, List<Pair> participants) {
        if (view == null) {
            return;
        }
        participants.add(new Pair<>(view, view.getTransitionName()));
    }

}
