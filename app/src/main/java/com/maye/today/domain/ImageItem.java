package com.maye.today.domain;


public class ImageItem {
    private String imageId;
    private String imagePaht;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImagePaht() {
        return imagePaht;
    }

    public void setImagePaht(String imagePaht) {
        this.imagePaht = imagePaht;
    }

}
