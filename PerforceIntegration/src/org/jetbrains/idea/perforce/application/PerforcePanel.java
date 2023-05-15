/*
 * Copyright 2000-2005 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.idea.perforce.application;

import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.idea.perforce.perforce.PerforceSettings;

import javax.swing.*;

public interface PerforcePanel {
  void updateFrom(PerforceSettings settings);

  void applyTo(PerforceSettings settings) throws ConfigurationException;

  default void cancel(PerforceSettings settings) {}

  boolean isModified(PerforceSettings settings);

  JPanel getPanel();
}
