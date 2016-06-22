	private void renderObject(int y, WorldController worldController,
			CollisionMap collisionMap, int type, int plane, int x,
			int objectId, int face) {
		if (lowMemory && (renderRuleFlags[0][x][y] & 2) == 0) {
			if ((renderRuleFlags[plane][x][y] & 0x10) != 0)
				return;
			if (getVisibilityPlane(y, plane, x) != Region.plane)
				return;
		}
		if (plane < lowestPlane)
			lowestPlane = plane;
		int vertexHeightSW = vertexHeights[plane][x][y];
		int vertexHeightSE = vertexHeights[plane][x + 1][y];
		int vertexHeightNE = vertexHeights[plane][x + 1][y + 1];
		int vertexHeightNW = vertexHeights[plane][x][y + 1];
		int drawHeight = vertexHeightSW + vertexHeightSE + vertexHeightNE
				+ vertexHeightNW >> 2;
		GameObjectDefinition objectDefinition = GameObjectDefinition
				.getDefinition(objectId);
		int hash = x + (y << 7) + (objectId << 14) + 0x40000000;
		if (!objectDefinition.hasActions)
			hash += 0x80000000;
		byte config = (byte) ((face << 6) + type);
		if (type == 22) {
			if (lowMemory && !objectDefinition.hasActions
					&& !objectDefinition.unknownAttribute)
				return;
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(22, face,
						vertexHeightSW, vertexHeightSE, vertexHeightNE,
						vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, face, 22, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addGroundDecoration(x, y, plane, drawHeight, hash,
					((animable)), config);
			if (objectDefinition.solid && objectDefinition.hasActions
					&& collisionMap != null)
				collisionMap.markBlocked(x, y);
			return;
		}
		if (type == 10 || type == 11) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(10, face,
						vertexHeightSW, vertexHeightSE, vertexHeightNE,
						vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, face, 10, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			if (animable != null) {
				int rotation = 0;
				if (type == 11)
					rotation += 256;
				int sizeX;
				int sizeY;
				if (face == 1 || face == 3) {
					sizeX = objectDefinition.sizeY;
					sizeY = objectDefinition.sizeX;
				} else {
					sizeX = objectDefinition.sizeX;
					sizeY = objectDefinition.sizeY;
				}
				if (worldController.addEntityB(x, y, plane, drawHeight,
						rotation, sizeY, sizeX, hash, ((animable)),
						config)
						&& objectDefinition.castsShadow) {
					Model model;
					if (animable instanceof Model)
						model = (Model) animable;
					else
						model = objectDefinition.getModelAt(10, face,
								vertexHeightSW, vertexHeightSE, vertexHeightNE,
								vertexHeightNW, -1);
					if (model != null) {
						for (int _x = 0; _x <= sizeX; _x++) {
							for (int _y = 0; _y <= sizeY; _y++) {
								int intensity = model.diagonal2DAboveOrigin / 4;
								if (intensity > 30)
									intensity = 30;
								if (intensity > tileShadowIntensity[plane][x
										+ _x][y + _y])
									tileShadowIntensity[plane][x + _x][y + _y] = (byte) intensity;
							}

						}

					}
				}
			}
			if (objectDefinition.solid && collisionMap != null)
				collisionMap
						.markSolidOccupant(x, y, objectDefinition.sizeX,
								objectDefinition.sizeY, face,
								objectDefinition.walkable);
			return;
		}
		if (type >= 12) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(type, face,
						vertexHeightSW, vertexHeightSE, vertexHeightNE,
						vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, face, type, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addEntityB(x, y, plane, drawHeight, 0, 1, 1, hash,
					((animable)), config);
			if (type >= 12 && type <= 17 && type != 13 && plane > 0)
				tileCullingBitsets[plane][x][y] |= 0x924;
			if (objectDefinition.solid && collisionMap != null)
				collisionMap
						.markSolidOccupant(x, y, objectDefinition.sizeX,
								objectDefinition.sizeY, face,
								objectDefinition.walkable);
			return;
		}
		if (type == 0) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(0, face, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, face, 0, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallObject(x, y, plane, drawHeight,
					POWERS_OF_TWO[face], 0, hash, ((animable)),
					null, config);
			if (face == 0) {
				if (objectDefinition.castsShadow) {
					tileShadowIntensity[plane][x][y] = 50;
					tileShadowIntensity[plane][x][y + 1] = 50;
				}
				if (objectDefinition.wall)
					tileCullingBitsets[plane][x][y] |= 0x249;
			} else if (face == 1) {
				if (objectDefinition.castsShadow) {
					tileShadowIntensity[plane][x][y + 1] = 50;
					tileShadowIntensity[plane][x + 1][y + 1] = 50;
				}
				if (objectDefinition.wall)
					tileCullingBitsets[plane][x][y + 1] |= 0x492;
			} else if (face == 2) {
				if (objectDefinition.castsShadow) {
					tileShadowIntensity[plane][x + 1][y] = 50;
					tileShadowIntensity[plane][x + 1][y + 1] = 50;
				}
				if (objectDefinition.wall)
					tileCullingBitsets[plane][x + 1][y] |= 0x249;
			} else if (face == 3) {
				if (objectDefinition.castsShadow) {
					tileShadowIntensity[plane][x][y] = 50;
					tileShadowIntensity[plane][x + 1][y] = 50;
				}
				if (objectDefinition.wall)
					tileCullingBitsets[plane][x][y] |= 0x492;
			}
			if (objectDefinition.solid && collisionMap != null)
				collisionMap.markWall(y, face, x, type,
						objectDefinition.walkable);
			if (objectDefinition.offsetAmplifier != 16)
				worldController.method290(y, objectDefinition.offsetAmplifier,
						x, plane);
			return;
		}
		if (type == 1) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(1, face, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, face, 1, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallObject(x, y, plane, drawHeight,
					WALL_CORNER_ORIENTATION[face], 0, hash,
					((animable)), null, config);
			if (objectDefinition.castsShadow)
				if (face == 0)
					tileShadowIntensity[plane][x][y + 1] = 50;
				else if (face == 1)
					tileShadowIntensity[plane][x + 1][y + 1] = 50;
				else if (face == 2)
					tileShadowIntensity[plane][x + 1][y] = 50;
				else if (face == 3)
					tileShadowIntensity[plane][x][y] = 50;
			if (objectDefinition.solid && collisionMap != null)
				collisionMap.markWall(y, face, x, type,
						objectDefinition.walkable);
			return;
		}
		if (type == 2) {
			int orientation = face + 1 & 3;
			Animable animable1;
			Animable animable2;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null) {
				animable1 = objectDefinition.getModelAt(2, 4 + face,
						vertexHeightSW, vertexHeightSE, vertexHeightNE,
						vertexHeightNW, -1);
				animable2 = objectDefinition.getModelAt(2, orientation,
						vertexHeightSW, vertexHeightSE, vertexHeightNE,
						vertexHeightNW, -1);
			} else {
				animable1 = new GameObject(objectId, 4 + face, 2,
						vertexHeightSE, vertexHeightNE, vertexHeightSW,
						vertexHeightNW, objectDefinition.animationId, true);
				animable2 = new GameObject(objectId, orientation, 2,
						vertexHeightSE, vertexHeightNE, vertexHeightSW,
						vertexHeightNW, objectDefinition.animationId, true);
			}
			worldController.addWallObject(x, y, plane, drawHeight,
					POWERS_OF_TWO[face], POWERS_OF_TWO[orientation], hash,
					((animable1)), ((animable2)), config);
			if (objectDefinition.wall)
				if (face == 0) {
					tileCullingBitsets[plane][x][y] |= 0x249;
					tileCullingBitsets[plane][x][y + 1] |= 0x492;
				} else if (face == 1) {
					tileCullingBitsets[plane][x][y + 1] |= 0x492;
					tileCullingBitsets[plane][x + 1][y] |= 0x249;
				} else if (face == 2) {
					tileCullingBitsets[plane][x + 1][y] |= 0x249;
					tileCullingBitsets[plane][x][y] |= 0x492;
				} else if (face == 3) {
					tileCullingBitsets[plane][x][y] |= 0x492;
					tileCullingBitsets[plane][x][y] |= 0x249;
				}
			if (objectDefinition.solid && collisionMap != null)
				collisionMap.markWall(y, face, x, type,
						objectDefinition.walkable);
			if (objectDefinition.offsetAmplifier != 16)
				worldController.method290(y, objectDefinition.offsetAmplifier,
						x, plane);
			return;
		}
		if (type == 3) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(3, face, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, face, 3, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallObject(x, y, plane, drawHeight,
					WALL_CORNER_ORIENTATION[face], 0, hash,
					((animable)), null, config);
			if (objectDefinition.castsShadow)
				if (face == 0)
					tileShadowIntensity[plane][x][y + 1] = 50;
				else if (face == 1)
					tileShadowIntensity[plane][x + 1][y + 1] = 50;
				else if (face == 2)
					tileShadowIntensity[plane][x + 1][y] = 50;
				else if (face == 3)
					tileShadowIntensity[plane][x][y] = 50;
			if (objectDefinition.solid && collisionMap != null)
				collisionMap.markWall(y, face, x, type,
						objectDefinition.walkable);
			return;
		}
		if (type == 9) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(type, face,
						vertexHeightSW, vertexHeightSE, vertexHeightNE,
						vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, face, type, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addEntityB(x, y, plane, drawHeight, 0, 1, 1, hash,
					((animable)), config);
			if (objectDefinition.solid && collisionMap != null)
				collisionMap
						.markSolidOccupant(x, y, objectDefinition.sizeX,
								objectDefinition.sizeY, face,
								objectDefinition.walkable);
			return;
		}
		if (objectDefinition.adjustToTerrain)
			if (face == 1) {
				int temp = vertexHeightNW;
				vertexHeightNW = vertexHeightNE;
				vertexHeightNE = vertexHeightSE;
				vertexHeightSE = vertexHeightSW;
				vertexHeightSW = temp;
			} else if (face == 2) {
				int temp = vertexHeightNW;
				vertexHeightNW = vertexHeightSE;
				vertexHeightSE = temp;
				temp = vertexHeightNE;
				vertexHeightNE = vertexHeightSW;
				vertexHeightSW = temp;
			} else if (face == 3) {
				int temp = vertexHeightNW;
				vertexHeightNW = vertexHeightSW;
				vertexHeightSW = vertexHeightSE;
				vertexHeightSE = vertexHeightNE;
				vertexHeightNE = temp;
			}
		if (type == 4) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(4, 0, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, 0, 4, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallDecoration(x, y, plane, drawHeight, 0, 0,
					face * 512, hash, ((animable)), config,
					POWERS_OF_TWO[face]);
			return;
		}
		if (type == 5) {
			int offsetAmplifier = 16;
			int hash_ = worldController.getWallObjectHash(x, y, plane);
			if (hash_ > 0)
				offsetAmplifier = GameObjectDefinition
						.getDefinition(hash_ >> 14 & 0x7fff).offsetAmplifier;
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(4, 0, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, 0, 4, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallDecoration(x, y, plane, drawHeight,
					FACE_OFFSET_X[face] * offsetAmplifier, FACE_OFFSET_Y[face]
							* offsetAmplifier, face * 512, hash,
					((animable)), config, POWERS_OF_TWO[face]);
			return;
		}
		if (type == 6) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(4, 0, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, 0, 4, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallDecoration(x, y, plane, drawHeight, 0, 0,
					face, hash, ((animable)), config, 256);
			return;
		}
		if (type == 7) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(4, 0, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, 0, 4, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallDecoration(x, y, plane, drawHeight, 0, 0,
					face, hash, ((animable)), config, 512);
			return;
		}
		if (type == 8) {
			Animable animable;
			if (objectDefinition.animationId == -1
					&& objectDefinition.childIds == null)
				animable = objectDefinition.getModelAt(4, 0, vertexHeightSW,
						vertexHeightSE, vertexHeightNE, vertexHeightNW, -1);
			else
				animable = new GameObject(objectId, 0, 4, vertexHeightSE,
						vertexHeightNE, vertexHeightSW, vertexHeightNW,
						objectDefinition.animationId, true);
			worldController.addWallDecoration(x, y, plane, drawHeight, 0, 0,
					face, hash, ((animable)), config, 768);
		}
	}
