/**
 * Copyright (C) 2022 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.cliffhangermc.cliff.task;

import io.github.cliffhangermc.cliff.util.GameUtil;
import org.apache.commons.io.FileUtils;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

/**
 * @author Enaium
 */
public class DownloadGameTask extends Task {
    @TaskAction
    public void download() {
        File clientJar = GameUtil.getClientFile(extension);
        try {
            if (!GameUtil.fileVerify(clientJar, GameUtil.getClientJarSha1(extension))) {
                File localJar = GameUtil.getLocalJar(extension.minecraft.version);
                if (GameUtil.fileVerify(localJar, GameUtil.getClientJarSha1(extension))) {
                    getLogger().info(String.format("Copy local client.jar form %s", clientJar.getAbsolutePath()));
                    FileUtils.copyFile(localJar, clientJar);
                } else {
                    getLogger().info("Download client.jar");
                    FileUtils.writeByteArrayToFile(clientJar, GameUtil.getClientJar(extension));
                }
            }
        } catch (IOException e) {
            getProject().getLogger().lifecycle(e.getMessage(), e);
        }

//        File serverJar = GameUtil.getServerFile(extension);
//        if (!GameUtil.fileVerify(serverJar, GameUtil.getServerJarSha1(extension))) {
//            getLogger().info("Download server.jar");
//            try {
//                FileUtils.writeByteArrayToFile(serverJar,GameUtil.getServerJar(extension));
//            } catch (IOException e) {
//                getProject().getLogger().lifecycle(e.getMessage(), e);
//            }
//        }
    }
}
