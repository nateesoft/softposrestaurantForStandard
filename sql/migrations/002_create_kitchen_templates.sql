-- Per-kitchen custom ticket templates (Kitchen Template designer).
-- KitchenTemplateService.ensureTable() runs this same statement at startup (Login).
-- Template text is stored through ThaiUtil.Unicode2ASCII because the DB is latin1.
CREATE TABLE IF NOT EXISTS kitchen_templates (
  kic_no     VARCHAR(5) NOT NULL,            -- kitchen number: 1..20 (printer kic1..kic20)
  enabled    CHAR(1)    NOT NULL DEFAULT 'N',-- Y = print this kitchen from the template instead of the built-in form
  template   TEXT       NOT NULL,
  updated_at DATETIME   NULL,
  PRIMARY KEY (kic_no)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
