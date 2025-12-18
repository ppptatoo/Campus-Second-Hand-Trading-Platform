-- 将所有早于 2025-11 的时间字段随机更新为 2025-11-01 到 2025-12-15 之间的日期
-- 注意：仅修改“创建/更新时间”等业务时间，不修改生日等个人信息字段

SET @d1 = DATE('2025-11-01');
SET @range_days = 45; -- 11月到12月中旬

-- user
UPDATE `user`
SET create_at = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE create_at IS NOT NULL AND STR_TO_DATE(create_at, '%Y-%m-%d') < '2025-11-01';

UPDATE `user`
SET last_login = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE last_login IS NOT NULL AND STR_TO_DATE(last_login, '%Y-%m-%d') < '2025-11-01';

-- address
UPDATE `address`
SET created_time = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE created_time IS NOT NULL AND created_time <> ''
  AND STR_TO_DATE(created_time, '%Y-%m-%d') < '2025-11-01';

UPDATE `address`
SET modified_time = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE modified_time IS NOT NULL AND modified_time <> ''
  AND STR_TO_DATE(modified_time, '%Y-%m-%d') < '2025-11-01';

-- carousel
UPDATE `carousel`
SET create_at = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE create_at IS NOT NULL AND create_at <> ''
  AND STR_TO_DATE(create_at, '%Y-%m-%d') < '2025-11-01';

-- comments
UPDATE `comments`
SET create_at = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE create_at IS NOT NULL AND create_at <> ''
  AND STR_TO_DATE(create_at, '%Y-%m-%d') < '2025-11-01';

-- reply
UPDATE `reply`
SET create_at = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE create_at IS NOT NULL AND create_at <> ''
  AND STR_TO_DATE(create_at, '%Y-%m-%d') < '2025-11-01';

-- notice
UPDATE `notice`
SET create_at = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE create_at IS NOT NULL AND create_at <> ''
  AND STR_TO_DATE(create_at, '%Y-%m-%d') < '2025-11-01';

-- orders（如果能被解析为日期则更新）
UPDATE `orders`
SET create_at = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE create_at IS NOT NULL AND create_at <> ''
  AND STR_TO_DATE(create_at, '%Y-%m-%d') IS NOT NULL
  AND STR_TO_DATE(create_at, '%Y-%m-%d') < '2025-11-01';

-- goods：开始时间/擦亮时间/结束时间
UPDATE `goods`
SET start_time  = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE start_time IS NOT NULL AND start_time <> ''
  AND STR_TO_DATE(start_time, '%Y-%m-%d') < '2025-11-01';

UPDATE `goods`
SET polish_time = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE polish_time IS NOT NULL AND polish_time <> ''
  AND STR_TO_DATE(polish_time, '%Y-%m-%d') < '2025-11-01';

UPDATE `goods`
SET end_time    = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE end_time IS NOT NULL AND end_time <> ''
  AND STR_TO_DATE(end_time, '%Y-%m-%d') < '2025-11-01';

-- wanted
UPDATE `wanted`
SET create_at = DATE_FORMAT(@d1 + INTERVAL FLOOR(RAND()*@range_days) DAY, '%Y-%m-%d')
WHERE create_at IS NOT NULL AND create_at <> ''
  AND STR_TO_DATE(create_at, '%Y-%m-%d') < '2025-11-01';
