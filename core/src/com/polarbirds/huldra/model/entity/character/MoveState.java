package com.polarbirds.huldra.model.entity.character;

/**
 * Created by Harald Wilhelmsen on 31/7/2015.
 */
public enum MoveState {
  IDLE, // on ground, still
  WALKING, // on ground moving
  DANCING, // on ground, dancing
  HANGING, // hanging from platform
  CLIMBING, // climbing a ladder
  FALLING, // in air
  FLYING, // in air but controlled
  ;
}
