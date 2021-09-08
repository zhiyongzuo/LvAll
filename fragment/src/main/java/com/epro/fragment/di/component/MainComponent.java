//package com.epro.fragment.di.component;
//
//
//import com.epro.fragment.di.module.MainModule;
//import com.epro.fragment.mvp.contract.MainContract;
//
//import com.epro.fragment.mvp.ui.fragment.MainFragment;
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
//@Component(modules = MainModule.class, dependencies = AppComponent.class)
//public interface MainComponent {
//    void inject(MainFragment fragment);
//
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        MainComponent.Builder view(MainContract.View view);
//
//        MainComponent.Builder appComponent(AppComponent appComponent);
//
//        MainComponent build();
//    }
//}