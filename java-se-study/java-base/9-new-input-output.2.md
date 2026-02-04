## Path

Primary interface of the new `NIO.2` input output implementation, used to locate a file in a file system it is
typically represent a system dependent file path. The Path represents a hierarchical structure and compose of a
sequence of file name elements. The Path interface is accompanied with a Paths class that is used to construct path
elements, it provides static utility methods to make new paths out of other paths or string components

## Properties

## Interface

## Implementations

There are a couple of implementations for the Path interface, but the general ones is `Unix, Windows Zip and Jrt`
these implementations cover the basic path types, by the default JDK

## Files

The static helper class that is used to provide common utility methods that work with the Path interface and also
provide a bridge

## Caveats

