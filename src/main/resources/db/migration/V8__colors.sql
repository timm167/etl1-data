CREATE TABLE colors (
    id SERIAL PRIMARY KEY,
    hex INT
);

INSERT INTO colors (hex) VALUES
     (16711680),  -- #FF0000 red
     (65280),     -- #00FF00 green
     (255),       -- #0000FF blue
     (16776960),  -- #FFFF00 yellow
     (16711935),  -- #FF00FF magenta
     (65535),     -- #00FFFF cyan
     (8421504),   -- #808080 gray
     (0),         -- #000000 black
     (16777215),  -- #FFFFFF white
     (10040064),  -- #990000 dark red
     (3329330),   -- #32CD32 lime green
     (16753920),  -- #FFA500 orange
     (11259375),  -- #ABCDEF
     (8947848),   -- #888888
     (1193046),   -- #123456
     (15790320),  -- #F0F0F0
     (2558160),   -- #272727
     (4473924),   -- #444444
     (10066329),  -- #999999
     (13421772);  -- #CCCCCC

