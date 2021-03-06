package com.softpos.pos.core.controller;

import javax.swing.*;

public class TextAreaEditor extends DefaultCellEditor {
  public TextAreaEditor() {
    super(new JTextField());
    final JTextArea textArea = new JTextArea();
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setBorder(null);
    editorComponent = scrollPane;

    delegate = new DefaultCellEditor.EditorDelegate() {
      @Override
      public void setValue(Object value) {
        textArea.setText((value != null) ? value.toString() : "");
      }
      @Override
      public Object getCellEditorValue() {
        return textArea.getText();
      }
    };
  }
}
