package com.kasuo.crawler.domain.org;

import java.util.ArrayList;
import java.util.List;

public class AreaInfo {
    public int district;
    public String areacode;
    public String postcode;
    public List<String> alias = new ArrayList();

    public AreaInfo() {
    }
}
