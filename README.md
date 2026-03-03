# 📈 TamagotchiDataCollector(Prototype) - Daily Life & Habit Tracker

**TamagotchiDataCollector(Prototype)** is a gamified daily tracking desktop application built with JavaFX. It allows users to log their daily habits, diet, productivity, and social interactions, translating those real-world actions into RPG-style statistics for a personalized digital Avatar.

All daily entries are safely harvested and exported into a locally stored CSV file, allowing users to track their progress, build graphs, and analyze their life trends over time in Excel.

## ✨ Features

* **🎮 Gamified Life Stats:** Your daily choices directly impact your Avatar's 7 core attributes:
  * ❤️ **Health** (Impacted by diet, sleep, and alcohol)
  * 🧠 **Intellect** (Impacted by reading, hobbies, and study)
  * ⚡ **Dopamine** (Impacted by socializing, gaming, and scrolling)
  * 🏃 **Exercise** (Impacted by daily sports)
  * 💤 **Sleep Management** (Impacted by sleep duration and consistency)
  * 🤝 **Social** (Impacted by meeting friends, new people, and relationships)
  * 🛡️ **Duty** (Impacted by work/study, damaged by heavy procrastination)
* **🎨 Avatar Customization:** Customize your avatar's skin tone, hairstyle, hair color, and outfit. (Fetches dynamically via web API).
* **📊 Comprehensive Data Harvest:** Every slider, checkbox, and stat is logged into `daily_tracker_history.csv` with a precise date and time stamp. Formatted specifically for European Excel standards (semicolon delimited).
* **🔍 Daily Reviews:** Track your overall mood (1-10) and your daily goal achievement percentage.

## 🛠️ Technology Stack

* **Language:** Java
* **GUI Framework:** JavaFX (FXML)
