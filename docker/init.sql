CREATE TABLE team
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(100) NOT NULL,
    `created_at` DATETIME     NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE member
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT,
    `team_id`       BIGINT,
    `name`          VARCHAR(50) NOT NULL,
    `age`           INT         NOT NULL,
    `sex`           TINYINT     NOT NULL,
    `profile_image` BLOB,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
);
