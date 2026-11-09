-- Queue system for businessType=FoodCenter (connect.ini).
-- QueueService.ensureTable() runs this same statement at startup (Login) when the
-- table does not exist, so executing it by hand is optional.
-- DB charset is latin1 on purpose (legacy programs share the DB) - Thai/Lao text
-- is stored through ThaiUtil.Unicode2ASCII like every other table.
CREATE TABLE IF NOT EXISTS queue_works (
  queue_id           INT UNSIGNED NOT NULL AUTO_INCREMENT,
  queue_date         DATE         NOT NULL,                  -- business day, queue_no restarts at 1 each day
  queue_no           INT UNSIGNED NOT NULL,                  -- running number within queue_date
  queue_createdate   DATETIME     NOT NULL,
  queue_status       VARCHAR(10)  NOT NULL DEFAULT 'Pending',-- Pending | Prepare | Serve | Done | Cancel
  queue_from_table   VARCHAR(20)  NOT NULL DEFAULT '',
  queue_billno       VARCHAR(30)  NOT NULL DEFAULT '',
  queue_customer     VARCHAR(100) NOT NULL DEFAULT '',
  queue_macno        VARCHAR(10)  NOT NULL DEFAULT '',
  queue_cashier      VARCHAR(30)  NOT NULL DEFAULT '',
  queue_prepare_date DATETIME     NULL,
  queue_serve_date   DATETIME     NULL,
  queue_done_date    DATETIME     NULL,
  PRIMARY KEY (queue_id),
  UNIQUE KEY uq_queue_day_no (queue_date, queue_no),
  KEY idx_queue_status (queue_date, queue_status)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
