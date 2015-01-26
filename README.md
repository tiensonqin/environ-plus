environ-plus
============

# Based on Environ

Environ is a Clojure library for managing environment settings from a
number of different sources. It works well for applications following
the [12 Factor App](http://12factor.net/) pattern.

Currently, Environ-plus supports four sources, resolved in the following
order:

1. config/test.clj
2. config/development.clj
3. config/ci.clj
4. config/production.clj

## Installation

Include the following dependency in your `project.clj` file:

```clojure
:dependencies [[environ-plus "0.1.1"]]
```

## Usage

Add export ENVIRONMENT="development" to .zshrc or .bashrc

## License

Copyright © 2014 James Reeves

Distributed under the Eclipse Public License, the same as Clojure.
