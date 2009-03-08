/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.file.remote;

import org.apache.camel.component.file.GenericFile;

/**
 * Represents a remote file of some sort of backing object
 *
 * @param <T> the type of file that these remote endpoints provide
 */
public class RemoteFile<T> extends GenericFile<T> implements Cloneable {

    private String hostname;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    @Override
    public boolean needToNormalize() {
        return false;
    }
    
    public String getFileSeparator() {
        // always use / as separator for FTP
        return "/";
    }

    public RemoteFile<T> copyFrom(RemoteFile<T> source) {
        RemoteFile<T> result = (RemoteFile<T>) source.clone();
        result.setAbsolute(source.isAbsolute());
        result.setAbsoluteFilePath(source.getAbsoluteFilePath());
        result.setRelativeFilePath(source.getRelativeFilePath());
        result.setFileName(source.getFileName());
        result.setFileLength(source.getFileLength());
        result.setLastModified(source.getLastModified());
        result.setFile(source.getFile());
        result.setBody(source.getBody());
        result.setBinding(source.getBinding());
        result.setHostname(source.getHostname());
        return result;
    }
}
