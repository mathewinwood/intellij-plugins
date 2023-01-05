// This is a generated file. Not intended for manual editing.
package org.intellij.prisma.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.intellij.prisma.lang.psi.PrismaElementTypes.*;
import org.intellij.prisma.lang.psi.*;

public class PrismaModelDeclarationImpl extends PrismaTableEntityDeclarationMixin implements PrismaModelDeclaration {

  public PrismaModelDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PrismaVisitor visitor) {
    visitor.visitModelDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PrismaVisitor) accept((PrismaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PrismaFieldDeclarationBlock getFieldDeclarationBlock() {
    return findChildByClass(PrismaFieldDeclarationBlock.class);
  }

  @Override
  @Nullable
  public PsiElement getIdentifier() {
    return findChildByType(IDENTIFIER);
  }

}
