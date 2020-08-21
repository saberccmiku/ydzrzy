package com.thxx.ydzrzy.entity;

public class Menu {
    private String id;
    private String defaultDrawable;
    private String selectedDrawable;
    private String title;
    private boolean isSelected;

    public Menu() {
    }

    public Menu(String id, String defaultDrawable, String selectedDrawable, String title, boolean isSelected) {
        this.id = id;
        this.defaultDrawable = defaultDrawable;
        this.selectedDrawable = selectedDrawable;
        this.title = title;
        this.isSelected = isSelected;
    }

    public String getDefaultDrawable() {
        return defaultDrawable;
    }

    public void setDefaultDrawable(String defaultDrawable) {
        this.defaultDrawable = defaultDrawable;
    }

    public String getSelectedDrawable() {
        return selectedDrawable;
    }

    public void setSelectedDrawable(String selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
