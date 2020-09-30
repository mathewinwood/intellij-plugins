/*
 * Copyright (C) 2020 ThoughtWorks, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.thoughtworks.gauge.stub;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.IOUtil;
import com.intellij.util.io.KeyDescriptor;
import com.thoughtworks.gauge.language.ConceptFileType;
import com.thoughtworks.gauge.language.SpecFileType;
import com.thoughtworks.gauge.language.psi.impl.ConceptStepImpl;
import com.thoughtworks.gauge.language.psi.impl.SpecStepImpl;
import com.thoughtworks.gauge.util.GaugeUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public final class GaugeFileStubIndex extends FileBasedIndexExtension<String, Set<Integer>> {
  @NonNls
  public static final ID<String, Set<Integer>> NAME = ID.create("GaugeFileStubIndex");

  @NotNull
  @Override
  public ID<String, Set<Integer>> getName() {
    return NAME;
  }

  @NotNull
  @Override
  public DataIndexer<String, Set<Integer>, FileContent> getIndexer() {
    return fileContent -> {
      Set<Integer> offsets = new HashSet<>();
      List<PsiElement> steps = new ArrayList<>();
      PsiFile psiFile = fileContent.getPsiFile();
      if (fileContent.getFileType() instanceof SpecFileType) {
        steps = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, SpecStepImpl.class));
      }
      else if (fileContent.getFileType() instanceof ConceptFileType) {
        steps = new ArrayList<>(PsiTreeUtil.collectElementsOfType(psiFile, ConceptStepImpl.class));
      }
      steps.forEach((s) -> offsets.add(s.getTextOffset()));
      return Collections.singletonMap(fileContent.getFile().getPath(), offsets);
    };
  }

  @NotNull
  @Override
  public KeyDescriptor<String> getKeyDescriptor() {
    return EnumeratorStringDescriptor.INSTANCE;
  }

  @NotNull
  @Override
  public DataExternalizer<Set<Integer>> getValueExternalizer() {
    return new DataExternalizer<>() {

      @Override
      public void save(@NotNull DataOutput dataOutput, Set<Integer> integers) throws IOException {
        String offsets = "";
        for (Integer integer : integers) offsets += integer.toString() + ",";
        IOUtil.writeUTF(dataOutput, offsets);
      }

      @Override
      public Set<Integer> read(@NotNull DataInput dataInput) throws IOException {
        Set<Integer> offsets = new HashSet<>();
        String s = IOUtil.readUTF(dataInput);
        for (String offset : s.split(",")) {
          if (!offset.isEmpty()) {
            offsets.add(Integer.parseInt(offset));
          }
        }
        return offsets;
      }
    };
  }

  @NotNull
  @Override
  public FileBasedIndex.InputFilter getInputFilter() {
    return new DefaultFileTypeSpecificInputFilter(SpecFileType.INSTANCE, ConceptFileType.INSTANCE) {
      @Override
      public boolean acceptInput(@NotNull VirtualFile virtualFile) {
        return virtualFile.getExtension() != null && GaugeUtil.isGaugeFile(virtualFile);
      }
    };
  }

  @Override
  public boolean dependsOnFileContent() {
    return true;
  }

  @Override
  public int getVersion() {
    return 1;
  }
}
