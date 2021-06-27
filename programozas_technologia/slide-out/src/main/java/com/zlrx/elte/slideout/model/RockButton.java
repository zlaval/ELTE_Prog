package com.zlrx.elte.slideout.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.JButton;

@Getter
@AllArgsConstructor
public class RockButton {
    private final JButton jButton;
    private final Dim2D dimension;
}
