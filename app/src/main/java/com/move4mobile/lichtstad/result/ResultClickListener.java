package com.move4mobile.lichtstad.result;

import com.move4mobile.lichtstad.databinding.ListItemResultBinding;
import com.move4mobile.lichtstad.model.Result;

public interface ResultClickListener {

    void onResultClick(Result result, ListItemResultBinding item);

}
