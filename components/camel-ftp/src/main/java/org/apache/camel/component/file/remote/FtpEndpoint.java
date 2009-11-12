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

import java.util.Map;

import org.apache.camel.FailedToCreateConsumerException;
import org.apache.camel.FailedToCreateProducerException;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileProducer;
import org.apache.camel.util.IntrospectionSupport;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

/**
 * FTP endpoint
 */
public class FtpEndpoint extends RemoteFileEndpoint<FTPFile> {

    private FTPClient ftpClient;
    private FTPClientConfig ftpClientConfig;
    private Map<String, Object> ftpClientParameters;
    private Map<String, Object> ftpClientConfigParameters;

    public FtpEndpoint() {
    }

    public FtpEndpoint(String uri, FtpComponent component, RemoteFileConfiguration configuration) {
        super(uri, component, configuration);
    }

    @Override
    public String getScheme() {
        return "ftp";
    }

    @Override
    protected RemoteFileConsumer<FTPFile> buildConsumer(Processor processor) {
        try {
            return new FtpConsumer(this, processor, createRemoteFileOperations());
        } catch (Exception e) {
            throw new FailedToCreateConsumerException(this, e);
        }
    }

    protected GenericFileProducer<FTPFile> buildProducer() {
        try {
            return new RemoteFileProducer<FTPFile>(this, createRemoteFileOperations());
        } catch (Exception e) {
            throw new FailedToCreateProducerException(this, e);
        }
    }
    
    protected RemoteFileOperations<FTPFile> createRemoteFileOperations() throws Exception {
        // configure ftp client
        FTPClient client = ftpClient;
        if (client == null) {
            // must use a new client if not explicit configured to use a custom client
            client = new FTPClient();
        }

        if (ftpClientParameters != null) {
            IntrospectionSupport.setProperties(client, ftpClientParameters);
        }
        if (ftpClientConfigParameters != null) {
            // client config is optional so create a new one if we have parameter for it
            if (ftpClientConfig == null) {
                ftpClientConfig = new FTPClientConfig();
            }
            IntrospectionSupport.setProperties(ftpClientConfig, ftpClientConfigParameters);
        }

        FtpOperations operations = new FtpOperations(client, getFtpClientConfig());
        operations.setEndpoint(this);
        return operations;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public FTPClientConfig getFtpClientConfig() {
        return ftpClientConfig;
    }

    public void setFtpClientConfig(FTPClientConfig ftpClientConfig) {
        this.ftpClientConfig = ftpClientConfig;
    }

    /**
     * Used by FtpComponent to provide additional parameters for the FTPClient
     */
    void setFtpClientParameters(Map<String, Object> ftpClientParameters) {
        this.ftpClientParameters = ftpClientParameters;
    }

    /**
     * Used by FtpComponent to provide additional parameters for the FTPClientConfig
     */
    void setFtpClientConfigParameters(Map<String, Object> ftpClientConfigParameters) {
        this.ftpClientConfigParameters = ftpClientConfigParameters;
    }
}
