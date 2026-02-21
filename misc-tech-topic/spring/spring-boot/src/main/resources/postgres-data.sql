BEGIN;

INSERT INTO videos (name, description) VALUES
    ('Neon Harbor', 'A disillusioned dockworker uncovers a smuggling ring and must choose between quiet survival and loud justice.'),
    ('Clockwork Orchard', 'In a town where timekeeping is law, a botanist finds a tree that blooms one hour into the future.'),
    ('Paper Satellites', 'Two rival students launch homemade satellites to prove a theory, then catch a signal that should not exist.'),
    ('Silent Latitude', 'A stranded polar radio operator hears a voice on a dead frequency and follows it across the ice.'),
    ('Midnight at Kilometer 12', 'A night bus keeps stopping at the same marker, and the passengers realize the route is looping for a reason.'),
    ('The Glass Cartographer', 'A mapmaker who etches cities into glass discovers his newest map is changing on its own.'),
    ('Sunset Protocol', 'A cybersecurity analyst finds an algorithm predicting blackouts—then learns it was written by someone inside the grid.'),
    ('Echoes in the Elevator', 'Every floor reveals a different year, and a building maintenance worker tries to fix a lift that is breaking reality.'),
    ('Ash & Apricot', 'After a wildfire, a chef and a firefighter rebuild a community kitchen that becomes the town’s second chance.'),
    ('Violet Nomads', 'A band of traveling musicians can erase memories with a song, until one night they erase the wrong one.'),
    ('The Last Lantern Maker', 'When the sun dims for unknown reasons, a craftsman’s lanterns become a currency—and a target.'),
    ('Mosaic of Tomorrow', 'A street artist paints murals that appear the next day in real life, drawing the attention of a secret agency.'),
    ('Northbound Without Maps', 'A mountaineer follows handwritten directions from a stranger and finds a village that does not appear on any chart.'),
    ('Beneath the Library Steps', 'A librarian discovers a hidden staircase leading to an archive where books remember the readers.'),
    ('Red Signal Dawn', 'A ham radio hobbyist intercepts a distress call from a city that has been abandoned for decades.'),
    ('The Meridian Thief', 'A skilled pickpocket steals a compass that points to people instead of places, and cannot put it down.'),
    ('Rooftop Weather', 'A meteorologist and a skateboarder chase a microclimate that only forms above the city’s tallest roofs.'),
    ('Tides of Copper', 'In a coastal factory town, an engineer learns the ocean is carrying metal messages from a missing ship.'),
    ('The Ink Between Us', 'A tattoo artist’s ink reveals hidden truths, forcing clients to face what they have been avoiding.'),
    ('Garden of Small Miracles', 'A retired teacher cultivates a community garden where every plant heals something different—at a cost.'),
    ('Seven Minutes to Winter', 'A commuter relives the same seven minutes before a storm, trying to save someone he has never met.'),
    ('Concrete Fireflies', 'Kids in a gray district build glowing drones to light their nights, accidentally exposing a long-buried scandal.'),
    ('The Borrowed Skyline', 'A photographer captures a skyline that vanishes from the world the next morning, and the city demands answers.'),
    ('Velvet Static', 'A late-night DJ receives anonymous vinyl records that predict callers’ futures—until one record predicts his.'),
    ('A Train Called Elsewhere', 'A new platform appears at the station, and a conductor must decide who gets to board and who must stay.'),
    ('The Algorithm of Rain', 'A data scientist learns the weather can be computed, then discovers someone is editing the forecast on purpose.'),
    ('Crescent Under Glass', 'A watchmaker builds a lunar model that starts controlling the tides, drawing a sailor into its orbit.'),
    ('Hollow City Postcards', 'A travel blogger receives postcards from a city with no roads in or out, each signed with her name.'),
    ('The Blue Door Agreement', 'Neighbors agree never to open a mysterious door in their hallway—until it opens from the inside.'),
    ('Starlight Maintenance', 'A satellite repair crew finds an extra module that was never launched and must trace who put it there.')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO roles (name) VALUES
    ('ADMIN'),
    ('EDITOR'),
    ('USER')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO authorities (name, "grant") VALUES
    ('VIDEO_READ',   'video:read'),
    ('VIDEO_CREATE', 'video:create'),
    ('VIDEO_UPDATE', 'video:update'),
    ('VIDEO_DELETE', 'video:delete'),
    ('USER_MANAGE',  'user:manage'),
    ('USER_LIST',    'user:list')
    ON CONFLICT (name) DO NOTHING;

WITH r AS (SELECT id FROM roles WHERE name = 'ADMIN'),
a AS (SELECT id FROM authorities)
INSERT INTO role_authority (role_id, authority_id)
    SELECT r.id, a.id
    FROM r CROSS JOIN a
    ON CONFLICT DO NOTHING;

WITH r AS (SELECT id FROM roles WHERE name = 'EDITOR'),
a AS (
    SELECT id FROM authorities
    WHERE name IN ('VIDEO_READ', 'VIDEO_CREATE', 'VIDEO_UPDATE', 'USER_LIST')
)
INSERT INTO role_authority (role_id, authority_id)
    SELECT r.id, a.id
    FROM r CROSS JOIN a
    ON CONFLICT DO NOTHING;

WITH r AS (SELECT id FROM roles WHERE name = 'USER'),
a AS (SELECT id FROM authorities WHERE name = 'VIDEO_READ')
INSERT INTO role_authority (role_id, authority_id)
    SELECT r.id, a.id
    FROM r CROSS JOIN a
    ON CONFLICT DO NOTHING;

INSERT INTO users (username, password, role_id) VALUES
    ('admin',  'admin123',  (SELECT id FROM roles WHERE name = 'ADMIN')),
    ('editor', 'editor123', (SELECT id FROM roles WHERE name = 'EDITOR')),
    ('user1',  'user123',   (SELECT id FROM roles WHERE name = 'USER')),
    ('user2',  'user123',   (SELECT id FROM roles WHERE name = 'USER'))
    ON CONFLICT (username) DO NOTHING;

COMMIT;
