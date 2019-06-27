package com.anfly.bigfly.base;

/**
 * @author Anfly
 * @date 2019/6/4
 * description：
 */
public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {
    protected M baseModel;
    protected V baseView;

    public void setBaseModel(M baseModel) {
        this.baseModel = baseModel;
    }

    public void setBaseView(V baseView) {
        this.baseView = baseView;
    }
}
