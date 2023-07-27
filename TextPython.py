import whisper

import time

start = time.time()
model = whisper.load_model("tiny")
result = model.transcribe("10sec.wav")
end = time.time()
print("............")
print(result)
print(format(end-start))
