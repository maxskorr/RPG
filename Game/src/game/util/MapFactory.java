package game.util;

import game.core.GameWorld;
import game.map.model.LevelMap;
import game.map.model.Tile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static game.util.GameOptions.*;

/**
 * Created by Max on 6/28/2014.
 *
 * Отвечает за загрузку карт и их создание.
 */
public class MapFactory {
    private final MapFactory instance;

    public MapFactory getInstance() {
        return instance;
    }

    private MapFactory() {
        this.instance = this;
    }

    public static LevelMap generateRandomMap(final int width, final int height, final GameWorld gameWorld) {
        final ArrayList<ArrayList<Tile>> tiles = new ArrayList<>(height);

        for (int i = 0; i < height; i++) {
            tiles.add(new ArrayList<Tile>(width));

            for (int j = 0; j < width; j++) {
                final int x = j * TILE_SIZE;
                final int y = i * TILE_SIZE;

                final int tile_id;

                if ((i == 0) || (i == (height - 1))
                        || (j == 0) || (j == (width - 1))) {
                    // Wall tile
                    tile_id = 0;
                } else {
                    tile_id = Math.abs(ResourceManager.random.nextInt() % (TILE_TYPE.values().length - 1));
                }

                final TILE_TYPE tile_type = TILE_TYPE.values()[tile_id];
                final Tile tile = TileFactory.make(x, y, tile_type, gameWorld);

                tiles.get(i).add(tile);

            }
        }

        return new LevelMap(tiles);
    }

    public static LevelMap loadMap(final String filename, final GameWorld gameWorld) {
        final LevelMap levelMap;
        final ArrayList<ArrayList<Tile>> tiles_lists = new ArrayList<>();

        final URL fullFilepath = ResourceManager.getPath(ASSETS_MAPS_PATH + filename);
        File map_file;

        try {
            map_file = new File(fullFilepath.toURI());
        } catch (URISyntaxException e) {
            Logger.getLogger(MapFactory.class).error(e.getMessage());
            map_file = new File(fullFilepath.getPath());
        }

        try(BufferedReader br_lines = new BufferedReader(new FileReader(map_file))) {

            String line;
            for (int i = 0; (line = br_lines.readLine()) != null; i++) {
                final Scanner sc_numbers = new Scanner(line);
                final ArrayList<Tile> tiles_list = new ArrayList<>();
                tiles_lists.add(tiles_list);

                for (int j = 0; sc_numbers.hasNextInt(); j++) {
                    final int val = sc_numbers.nextInt();
                    final int y = i * TILE_SIZE;
                    final int x = j * TILE_SIZE;
                    final TILE_TYPE tile_type = TILE_TYPE.values()[val];
                    final Tile tile = TileFactory.make(x, y, tile_type, gameWorld);

                    tiles_list.add(tile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        levelMap = new LevelMap(tiles_lists);

        return levelMap;
    }

}
