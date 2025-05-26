# Système de Gestion d'Événements Distribué

## 📋 Description
Projet TP POO - Système complet de gestion d'événements utilisant les concepts avancés de la Programmation Orientée Objet.

## 🎯 Fonctionnalités Implémentées

### ✅ Design Patterns
- **Singleton**: GestionEvenements
- **Observer**: Notifications automatiques aux participants
- **Factory**: Création d'événements et participants
- **Strategy**: Tri des événements

### ✅ Concepts POO
- **Héritage**: Evenement → Conference/Concert, Participant → Organisateur
- **Polymorphisme**: Méthodes abstraites et interfaces
- **Encapsulation**: Getters/setters avec validation
- **Abstraction**: Classes abstraites et interfaces

### ✅ Gestion des Exceptions
- `CapaciteMaxAtteinteException`
- `EvenementDejaExistantException`
- `ParticipantDejaInscritException`

### ✅ Sérialisation
- **JSON**: Avec Jackson
- **XML**: Avec JAXB

### ✅ Programmation Moderne
- **Streams & Lambdas**: Recherche et filtrage
- **Programmation Asynchrone**: CompletableFuture
- **Collections Génériques**: ConcurrentHashMap, List<T>

## 🚀 Utilisation

### Compilation
```bash
mvn clean compile
```

### Tests
```bash
mvn test
```

### Exécution
```bash
mvn exec:java -Dexec.mainClass="com.evenements.MainApplication"
```

### Rapport de Couverture
```bash
mvn jacoco:report
```

## 📁 Structure du Projet
```
src/
├── main/java/com/evenements/
│   ├── model/              # Classes métier
│   ├── service/            # Services et interfaces
│   ├── factory/            # Factory Pattern
│   ├── strategy/           # Strategy Pattern
│   ├── observer/           # Observer Pattern
│   ├── serialization/      # JSON/XML
│   ├── exception/          # Exceptions personnalisées
│   └── MainApplication.java
└── test/java/com/evenements/
    └── test/               # Tests unitaires JUnit 5
```

## 🎨 Diagramme UML
Le système comprend:
- **Evenement** (abstraite) → Conference, Concert
- **Participant** → Organisateur
- **NotificationService** (interface)
- **GestionEvenements** (Singleton)
- Patterns Observer, Factory, Strategy

## 📊 Couverture de Tests
- Tests unitaires JUnit 5
- Couverture > 70% (JaCoCo)
- Tests d'intégration pour sérialisation

## 👤 Auteur
HEUMI BIATEU ARTHUR FRESNEL 
Projet TP POO - 2025