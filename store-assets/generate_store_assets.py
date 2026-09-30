#!/usr/bin/env python3
"""
Generates Google Play Store compliant assets for IEW Academy (iaseasyway-academy-app):
- 512x512 App Icon (32-bit PNG, square, full bleed)
- 1024x500 Feature Graphic (24-bit RGB PNG, 16:9 safe zone)
- Downscaled launcher icons for Android densities (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
"""

import os
from PIL import Image, ImageDraw, ImageFont

ASSET_DIR = os.path.dirname(os.path.abspath(__file__))
APP_DIR = os.path.join(ASSET_DIR, "..", "app", "src", "main", "res")

def create_app_icon():
    size = (512, 512)
    img = Image.new("RGBA", size, (10, 25, 47, 255)) # Navy900 background
    draw = ImageDraw.Draw(img)

    # Subtle gradient or inner shield
    draw.rounded_rectangle([16, 16, 496, 496], radius=96, fill=(17, 34, 64, 255), outline=(255, 200, 0, 255), width=8)

    # Emerald Green central emblem / book-torch badge
    draw.rounded_rectangle([80, 80, 432, 432], radius=64, fill=(88, 204, 2, 255))

    # Academic Cap & Stars symbol
    # Star 1, 2, 3
    draw.polygon([(256, 110), (268, 140), (300, 140), (274, 160), (284, 190), (256, 170), (228, 190), (238, 160), (212, 140), (244, 140)], fill=(255, 200, 0, 255))

    # Bold Letters "IEW"
    try:
        font_large = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", 96)
        font_small = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", 36)
    except Exception:
        font_large = ImageFont.load_default()
        font_small = ImageFont.load_default()

    draw.text((256, 260), "IEW", fill=(255, 255, 255, 255), font=font_large, anchor="mm")
    draw.text((256, 340), "ACADEMY", fill=(255, 200, 0, 255), font=font_small, anchor="mm")
    draw.text((256, 385), "IAS EASYWAY", fill=(255, 255, 255, 220), font=font_small, anchor="mm")

    icon_path = os.path.join(ASSET_DIR, "app_icon_512.png")
    img.save(icon_path, "PNG")
    print(f"[✓] Created App Icon (512x512): {icon_path}")

    # Generate densities
    densities = {
        "mipmap-mdpi": 48,
        "mipmap-hdpi": 72,
        "mipmap-xhdpi": 96,
        "mipmap-xxhdpi": 144,
        "mipmap-xxxhdpi": 192
    }
    for folder, dim in densities.items():
        target_folder = os.path.join(APP_DIR, folder)
        os.makedirs(target_folder, exist_ok=True)
        scaled = img.resize((dim, dim), Image.Resampling.LANCZOS)
        scaled.save(os.path.join(target_folder, "ic_launcher.png"), "PNG")
        scaled.save(os.path.join(target_folder, "ic_launcher_round.png"), "PNG")
        print(f"  • Scaled {folder}: {dim}x{dim}")

def create_feature_graphic():
    size = (1024, 500)
    img = Image.new("RGB", size, (10, 25, 47))
    draw = ImageDraw.Draw(img)

    # Accent decorative background curves
    draw.rectangle([0, 0, 1024, 12], fill=(255, 200, 0)) # Gold top trim
    draw.rectangle([0, 488, 1024, 500], fill=(88, 204, 2)) # Green bottom trim

    # Central emblem
    draw.rounded_rectangle([60, 60, 260, 440], radius=32, fill=(17, 34, 64), outline=(88, 204, 2), width=4)

    try:
        font_emblem = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", 52)
        font_h1 = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", 46)
        font_sub = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf", 22)
        font_tag = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", 18)
    except Exception:
        font_emblem = ImageFont.load_default()
        font_h1 = ImageFont.load_default()
        font_sub = ImageFont.load_default()
        font_tag = ImageFont.load_default()

    draw.text((160, 210), "IEW", fill=(255, 255, 255), font=font_emblem, anchor="mm")
    draw.text((160, 280), "ACADEMY", fill=(255, 200, 0), font=font_tag, anchor="mm")

    # Typography & Feature highlights
    draw.text((310, 110), "IEW Academy • IAS EasyWay", fill=(255, 200, 0), font=font_tag)
    draw.text((310, 160), "Play-to-Learn & Real-Time Exam Simulator", fill=(255, 255, 255), font=font_h1)

    # Feature Pills
    features = [
        "🎮 Duolingo Play (Streaks, Hearts, XP)",
        "⏱️ UPSC & MPSC Prelims Real-Time Simulator",
        "🎯 SSC CGL, Group C & Group D Test Series",
        "🏫 Class 5th till Graduation Foundation Hub",
        "🛡️ DRM & Anti-Screenshot Question Protection"
    ]
    y_pos = 230
    for feat in features:
        draw.rounded_rectangle([310, y_pos, 960, y_pos + 38], radius=8, fill=(17, 34, 64))
        draw.text((325, y_pos + 19), feat, fill=(255, 255, 255), font=font_sub, anchor="lm")
        y_pos += 48

    fg_path = os.path.join(ASSET_DIR, "feature_graphic_1024x500.png")
    img.save(fg_path, "PNG")
    print(f"[✓] Created Feature Graphic (1024x500): {fg_path}")

if __name__ == "__main__":
    create_app_icon()
    create_feature_graphic()
