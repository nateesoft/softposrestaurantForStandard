-- posuser.Password stores a bcrypt hash ($2a$10$..., 60 chars) instead of plain text.
--   * Widened to VARCHAR(255) so a stronger cost/algorithm later still fits.
--   * ascii_bin: a hash is ASCII-only and must compare byte-for-byte (case-sensitive).
-- Existing plaintext values must be hashed by an application tool (MySQL has no bcrypt);
-- logins check the hash in code (pos-web-api AuthRepository), never with "Password = ?".
ALTER TABLE `posuser`
  MODIFY `Password` VARCHAR(255) CHARACTER SET ascii COLLATE ascii_bin NOT NULL DEFAULT '';
