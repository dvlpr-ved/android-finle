package com.dkglabs.model.response;

import com.google.gson.annotations.SerializedName;

public class ImageUrlModel {

    @SerializedName("file")
    private FileDetails fileDetails;

    public FileDetails getFileDetails() {
        return fileDetails;
    }

    public void setFileDetails(FileDetails fileDetails) {
        this.fileDetails = fileDetails;
    }

    public static class FileDetails {

        @SerializedName("id")
        private int id;

        @SerializedName("filetype")
        private String fileType;

        @SerializedName("size")
        private int size;

        @SerializedName("directURL")
        private String directURL;

        @SerializedName("protected")
        private boolean isProtected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getDirectURL() {
            return directURL;
        }

        public void setDirectURL(String directURL) {
            this.directURL = directURL;
        }

        public boolean isProtected() {
            return isProtected;
        }

        public void setProtected(boolean aProtected) {
            isProtected = aProtected;
        }
    }
}
