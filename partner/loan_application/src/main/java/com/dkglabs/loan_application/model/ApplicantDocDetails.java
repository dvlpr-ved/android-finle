package com.dkglabs.loan_application.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.dkglabs.model.applyloan.DocDetails;

import java.io.Serializable;

import javax.xml.transform.sax.SAXResult;

public class ApplicantDocDetails implements Serializable, Parcelable {
    public Drawable icon;
    public DocDetails docDetails;
    boolean upLoadStats=false;
    String TITLE="";
    String userId="";
    String loneId="";
    int pre_post;
    boolean main_fullFill=true;
    boolean isFullFill=false;

    public boolean isFullFill() {
        return isFullFill;
    }

    public void setFullFill(boolean fullFill) {
        isFullFill = fullFill;
    }

    public boolean isMain_fullFill() {
        return main_fullFill;
    }

    public void setMain_fullFill(boolean main_fullFill) {
        this.main_fullFill = main_fullFill;
    }

    int preNumberType=0;
    int postNumberType=0;

    int fulFilNumberType=0;

    public int getPre_post() {
        return pre_post;
    }

    public int getFulFilNumberType() {
        return fulFilNumberType;
    }

    public void setFulFilNumberType(int fulFilNumberType) {
        this.fulFilNumberType = fulFilNumberType;
    }

    public int getPreNumberType() {
        return preNumberType;
    }

    public void setPreNumberType(int preNumberType) {
        this.preNumberType = preNumberType;
    }

    public int getPostNumberType() {
        return postNumberType;
    }

    public void setPostNumberType(int postNumberType) {
        this.postNumberType = postNumberType;
    }

    public int isPre_post() {
        return pre_post;
    }

    public void setPre_post(int pre_post) {
        this.pre_post = pre_post;
    }

    public String getLoneId() {
        return loneId;
    }

    public void setLoneId(String loneId) {
        this.loneId = loneId;
    }

    protected ApplicantDocDetails(Parcel in) {
        docDetails = in.readParcelable(DocDetails.class.getClassLoader());
        upLoadStats = in.readByte() != 0;
        TITLE = in.readString();
        userId = in.readString();
        position = in.readInt();
    }

    public static final Creator<ApplicantDocDetails> CREATOR = new Creator<ApplicantDocDetails>() {
        @Override
        public ApplicantDocDetails createFromParcel(Parcel in) {
            return new ApplicantDocDetails(in);
        }

        @Override
        public ApplicantDocDetails[] newArray(int size) {
            return new ApplicantDocDetails[size];
        }
    };

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public boolean isUpLoadStats() {
        return upLoadStats;
    }

    public void setUpLoadStats(boolean upLoadStats) {
        this.upLoadStats = upLoadStats;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    int position;

    public ApplicantDocDetails(){

    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public DocDetails getDocDetails() {
        return docDetails;
    }
    public void setDocDetails(DocDetails docDetails) {
        this.docDetails = docDetails;
    }

    @BindingAdapter("imageResource")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setBackground(drawable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(docDetails, flags);
        dest.writeByte((byte) (upLoadStats ? 1 : 0));
        dest.writeString(TITLE);
        dest.writeString(userId);
        dest.writeInt(position);
    }
}
