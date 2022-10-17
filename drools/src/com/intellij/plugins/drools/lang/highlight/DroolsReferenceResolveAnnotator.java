package com.intellij.plugins.drools.lang.highlight;

import com.intellij.analysis.AnalysisBundle;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.plugins.drools.lang.psi.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;
import org.jetbrains.annotations.NotNull;


public class DroolsReferenceResolveAnnotator implements Annotator {

  @Override
  public void annotate(@NotNull PsiElement node, @NotNull AnnotationHolder holder) {
    if (node instanceof DroolsReference) {
      if (node.getParent() instanceof DroolsStringId) return;
      if (node.getParent() instanceof DroolsQualifiedIdentifier && node.getParent().getParent() instanceof DroolsQualifiedName) return;
      if (node.getParent() instanceof DroolsSimpleName) return;
      if (node.getParent() instanceof DroolsAnnotation) return;
      if (node.getParent() instanceof DroolsParameter) return;

      ResolveResult[] resolveResults = ((DroolsReference)node).multiResolve(false);
      if (resolveResults.length == 0) {
        holder.newAnnotation(HighlightSeverity.ERROR, AnalysisBundle.message("error.cannot.resolve")).create();
      }
    }
  }
}