package lyu.klt.graduationdesign.view;

/**
 * Created by moon.zhong on 2015/3/9.
 */
public class PagerItem {
    /*item 的信息*/
    private String mMsg ;
    /*item的 title*/
    private String mTitle ;

    public PagerItem(String mTitle,String mMsg) {
        this.mMsg = mMsg;
        this.mTitle = mTitle;
    }

  

    public String getTitle() {
        return mTitle;
    }
}
