Queue call sounds (monitor with queue board, status Serve)
==========================================================

Language comes from connect.ini:  queueVoice,th   (th | lo | en, default th)

Most reliable: recorded clips. Create a folder per language and put these WAV files in it:

  sounds/th/call.wav   "ขอเชิญคิวหมายเลข"     (played first)
  sounds/th/n0.wav     "ศูนย์"
  sounds/th/n1.wav     "หนึ่ง"
  ...
  sounds/th/n9.wav     "เก้า"
  sounds/th/end.wav    "ค่ะ"                  (optional, played last)

By default the number is spoken digit by digit: queue 125 = call + n1 + n2 + n5 (+ end).

Thai "read as a quantity" (125 = หนึ่งร้อยยี่สิบห้า): set  queueSpeak,words  in connect.ini and add these clips
(queue 1-9999; anything else, or a missing clip, falls back to digit by digit):

  sounds/th/ten.wav       "สิบ"
  sounds/th/yi.wav        "ยี่"      (20-29 = ยี่สิบ...)
  sounds/th/et.wav        "เอ็ด"     (11, 21, 101 ... = ...เอ็ด)
  sounds/th/hundred.wav   "ร้อย"
  sounds/th/thousand.wav  "พัน"      (only if the queue can reach 1000)

  15 = ten + n5     21 = yi + ten + et     125 = n1 + hundred + yi + ten + n5
The whole call is repeated 3 times. A new language = a new folder (sounds/lo, sounds/en ...)
and queueVoice,<folder name> in connect.ini.

Recording tips: WAV (PCM, 16-bit, 44.1/48 kHz), no silence at the start/end of each clip,
similar volume for all clips, keep the folder next to connect.ini (the program's working folder).

If the clips are missing the program tries the OS voice (Windows needs an installed voice for the
language, e.g. th-TH; macOS uses "say"). If there is no such voice it plays a beep 3 times and writes a
warning to logs. To see which voices Windows has, run in PowerShell:

  Add-Type -AssemblyName System.Speech
  (New-Object System.Speech.Synthesis.SpeechSynthesizer).GetInstalledVoices() | % { $_.VoiceInfo.Name + ' ' + $_.VoiceInfo.Culture }
