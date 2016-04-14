/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cdancy.jenkins.rest.features;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.cdancy.jenkins.rest.BaseJenkinsApiLiveTest;

@Test(groups = "live", testName = "SystemApiLiveTest", singleThreaded = true)
public class JobsApiLiveTest extends BaseJenkinsApiLiveTest {

   @Test
   public void testCreateJob() {
      String config = payloadFromResource("/freestyle-project.xml");
      boolean success = api().create("DevTest", config);
      assertTrue(success);
   }

   @Test(dependsOnMethods = "testCreateJob")
   public void testCreateJobThatAlreadyExists() {
      String config = payloadFromResource("/freestyle-project.xml");
      boolean success = api().create("DevTest", config);
      assertFalse(success);
   }

   @Test(dependsOnMethods = "testCreateJobThatAlreadyExists")
   public void testSetDescription() {
      boolean success = api().description("DevTest", "RandomDescription");
      assertTrue(success);
   }

   @Test(dependsOnMethods = "testSetDescription")
   public void testGetDescription() {
      String output = api().description("DevTest");
      assertTrue(output.equals("RandomDescription"));
   }

   @Test(dependsOnMethods = "testGetDescription")
   public void testGetConfig() {
      String output = api().config("DevTest");
      assertNotNull(output);
   }

   @Test(dependsOnMethods = "testGetConfig")
   public void testUpdateConfig() {
      String config = payloadFromResource("/freestyle-project.xml");
      boolean success = api().config("DevTest", config);
      assertTrue(success);
   }

   @Test(dependsOnMethods = "testUpdateConfig")
   public void testDisableJob() {
      boolean success = api().disable("DevTest");
      assertTrue(success);
   }

   @Test(dependsOnMethods = "testDisableJob")
   public void testDisableJobAlreadyDisabled() {
      boolean success = api().disable("DevTest");
      assertTrue(success);
   }

   @Test(dependsOnMethods = "testDisableJobAlreadyDisabled")
   public void testEnableJob() {
      boolean success = api().enable("DevTest");
      assertTrue(success);
   }

   @Test(dependsOnMethods = "testEnableJob")
   public void testEnableJobAlreadyEnabled() {
      boolean success = api().enable("DevTest");
      assertTrue(success);
   }

   @Test(dependsOnMethods = "testEnableJobAlreadyEnabled")
   public void testDeleteJob() {
      boolean success = api().delete("DevTest");
      assertTrue(success);
   }

   @Test
   public void testDeleteJobNonExistent() {
      boolean success = api().delete(randomString());
      assertFalse(success);
   }

   @Test
   public void testGetConfigNonExistentJob() {
      String output = api().config(randomString());
      assertNull(output);
   }

   @Test
   public void testSetDescriptionNonExistentJob() {
      boolean success = api().description(randomString(), "RandomDescription");
      assertFalse(success);
   }

   @Test
   public void testGetDescriptionNonExistentJob() {
      String output = api().description(randomString());
      assertNull(output);
   }

   private JobsApi api() {
      return api.jobsApi();
   }
}