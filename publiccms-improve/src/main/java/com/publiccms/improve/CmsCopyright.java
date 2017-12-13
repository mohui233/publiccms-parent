package com.publiccms.improve;

import com.publiccms.common.base.Copyright;

public class CmsCopyright implements Copyright {
    public static String DATAFILEPATH;

    @Override
    public void init(String dataFilePath) {
        DATAFILEPATH = dataFilePath;
    }

    @Override
    public boolean verify() {
        return true;
    }
}
