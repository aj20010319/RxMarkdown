/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown.syntax.edit;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Layout;
import android.text.style.AlignmentSpan;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.live.EditToken;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation of syntax for center align.
 * It's not the real syntax in Markdown.
 * syntax:
 * "[content]"
 * <p>
 * Created by yuyidong on 16/6/30.
 */
class CenterAlignSyntax extends EditSyntaxAdapter {

    public CenterAlignSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
    }

    @NonNull
    @Override
    public List<EditToken> format(@NonNull Editable editable) {
        List<EditToken> editTokenList = new ArrayList<>();
        StringBuilder content = new StringBuilder(editable);
        Pattern p = Pattern.compile("^(\\[)(.*?)(\\])$", Pattern.MULTILINE);
        Matcher m = p.matcher(content);
        List<String> matchList = new ArrayList<>();//找到的
        while (m.find()) {
            matchList.add(m.group());
        }
        for (String match : matchList) {
            int index = content.indexOf(match);
            int length = match.length();
            editTokenList.add(new EditToken(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), index, index + length));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
            StringBuilder placeHolder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                placeHolder.append(" ");
            }
            content.replace(index, index + length, placeHolder.toString());
        }
        return editTokenList;
    }
}
