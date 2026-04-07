import os

base = r"c:\Users\hfern\OneDrive\Desktop\valleGrande2\valleGrande"
dirs = [
    "src/main/java/ap2/nombreapellido/model",
    "src/main/java/ap2/nombreapellido/repository", 
    "src/main/java/ap2/nombreapellido/rest",
    "src/main/java/ap2/nombreapellido/service/impl",
    "src/main/resources/sql",
    ".idea",
    "src/test/java/ap2/nombreapellido"
]

for d in dirs:
    path = os.path.join(base, d.replace("/", os.sep))
    os.makedirs(path, exist_ok=True)
    print(f"Created: {path}")

print("Done!")
