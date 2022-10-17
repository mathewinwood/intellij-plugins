/*
 * Copyright 2000-2011 JetBrains s.r.o.
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
package com.intellij.plugins.drools.lang.support;

import com.intellij.plugins.drools.DroolsFileType;
import com.intellij.plugins.drools.lang.psi.DroolsRhs;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.LocalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.UseScopeEnlarger;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class DroolsRhsScopeEnlarger extends UseScopeEnlarger {

  @Override
  public SearchScope getAdditionalUseScope(@NotNull PsiElement element) {
    final PsiFile file = element.getContainingFile();
    if (file == null || file.getFileType() != DroolsFileType.DROOLS_FILE_TYPE) return null;

    final DroolsRhs droolsRhs = PsiTreeUtil.getParentOfType(element, DroolsRhs.class);
    if (droolsRhs != null) {
      return new LocalSearchScope(droolsRhs);
    }
    return null;
  }
}
