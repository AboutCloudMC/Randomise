# Randomise Plugin

A Minecraft plugin that adds randomization features to enhance gameplay variety and challenge. Built with CloudConfig
integration for configuration management and database support.

## Features

- Recipe Randomization: Randomizes crafting recipes
- Block Drop Randomization: Changes what items drop from blocks
- Entity Drop Randomization: Modifies mob drop tables
- Multi-language Support: Configurable locale settings
- In-game GUI Controls: Easy configuration through item interface

## Configuration

### Database Setup

The plugin requires a database connection configured in `plugins/Randomise/config/database.yml`.

### Locale

- Default locale: en-EN
- Additional languages can be added to `locale/` directory
- Players can change their language in-game using the language selector item

## Commands

- `/randomise` - Main plugin command

## Installation

1. Place the plugin JAR in your server's `plugins` folder
2. Start the server to generate configuration files
3. Configure database settings in `database.yml`
4. Restart the server

## Requirements

- Bukkit/Spigot server
- CloudConfig API
- CloudDatabase API
