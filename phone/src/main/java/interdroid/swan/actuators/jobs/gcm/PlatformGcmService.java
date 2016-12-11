/*
 * Copyright 2007-present Evernote Corporation.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package interdroid.swan.actuators.jobs.gcm;

import interdroid.swan.actuators.jobs.Job;
import interdroid.swan.actuators.jobs.JobProxy;
import interdroid.swan.actuators.jobs.JobRequest;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

/**
 * @author rwondratschek
 */
public class PlatformGcmService extends GcmTaskService {

    @Override
    public int onRunTask(TaskParams taskParams) {
        int jobId = Integer.parseInt(taskParams.getTag());
        JobProxy.Common common = new JobProxy.Common(this, jobId);

        JobRequest request = common.getPendingRequest();
        if (request == null) {
            common.cleanUpOrphanedJob();
            return GcmNetworkManager.RESULT_FAILURE;
        }

        Job.Result result = common.executeJobRequest(request);
        if (Job.Result.SUCCESS.equals(result)) {
            return GcmNetworkManager.RESULT_SUCCESS;
        } else {
            return GcmNetworkManager.RESULT_FAILURE;
        }
    }
}
