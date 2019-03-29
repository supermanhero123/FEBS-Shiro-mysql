package cc.mrbird.common.util;

import java.util.List;

public class DifferenceAttr {
    private DifferenceAttr differenceAttr;
    private List<String> differenceAttrs;
    private List<DifferenceAttr> childrenDifference;

    public List<String> getDifferenceAttrs() {
        return differenceAttrs;
    }

    public void setDifferenceAttrs(List<String> differenceAttrs) {
        this.differenceAttrs = differenceAttrs;
    }

    public List<DifferenceAttr> getChildrenDifference() {
        return childrenDifference;
    }

    public void setChildrenDifference(List<DifferenceAttr> childrenDifference) {
        this.childrenDifference = childrenDifference;
    }

    public DifferenceAttr getDifferenceAttr() {
        return differenceAttr;
    }

    public void setDifferenceAttr(DifferenceAttr differenceAttr) {
        this.differenceAttr = differenceAttr;
    }

}
