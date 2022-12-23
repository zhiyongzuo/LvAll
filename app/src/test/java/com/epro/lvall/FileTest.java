package com.epro.lvall;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;

import org.junit.Test;

import java.io.File;

public class FileTest {

    @Test
    public void testFilePath() {
        String path = PathUtils.getExternalAppCachePath() + "tt.txt";
        FileUtils.createOrExistsFile(path);
        System.out.println(new File(path).getPath());
    }
}
