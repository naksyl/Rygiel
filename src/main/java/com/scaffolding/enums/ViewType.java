package com.scaffolding.enums;

public enum ViewType {
    MAIN_VIEW,
    STATISTIC_VIEW,
    OPTIONS_VIEW,
    DATABASE_VIEW,
    WELCOME_VIEW,
    CONTRACTOR_EDITOR_VIEW;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(String part: super.toString().split("_")){
            builder.append(part.charAt(0))
                    .append(part.substring(1).toLowerCase());
        }
        return builder.toString();
    }
}
