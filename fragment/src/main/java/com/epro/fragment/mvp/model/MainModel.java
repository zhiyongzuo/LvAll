//package com.epro.fragment.mvp.model;
//
//import android.app.Application;
//
//import com.google.gson.Gson;
//import com.jess.arms.integration.IRepositoryManager;
//import com.jess.arms.mvp.BaseModel;
//
//import com.jess.arms.di.scope.FragmentScope;
//
//import javax.inject.Inject;
//
//import com.epro.fragment.mvp.contract.MainContract;
//
//
///**
// * ================================================
// * Description:
// * <p>
// * Created by MVPArmsTemplate on 07/21/2020 11:13
// * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
// * <a href="https://github.com/JessYanCoding">Follow me</a>
// * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
// * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
// * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
// * ================================================
// */
//@FragmentScope
//public class MainModel extends BaseModel implements MainContract.Model {
//    @Inject
//    Gson mGson;
//    @Inject
//    Application mApplication;
//
//    @Inject
//    public MainModel(IRepositoryManager repositoryManager) {
//        super(repositoryManager);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        this.mGson = null;
//        this.mApplication = null;
//    }
//}