# Batch 23 Online 2 (C1) - API Response Adapter & Dynamic Decorators

## Problem Statement
Your company's internal service produces responses in XML format (legacy internal format, used throughout your codebase — cannot change):
```java
class LegacyInternalXMLService {
    String getDataAsXML() {
        return "<user><id>101</id><name>Ishrat</name></user>";
    }
}
```
A third-party client wants to consume your API but requires **JSON**. Client code is written against:
```java
interface ApiResponse {
    String getBody();
}
```

### Dynamic Feature Requirements
Per-request, the client can specify (via API call parameters/headers) optional transformations:
- **Encryption**: Response body encrypted before sending (`?encrypt=true`).
- **Compression**: Response body compressed before sending (`?compress=true`).
- **Combinations**: Both, either, or neither — decided per API call based on query parameters.

### Critical Constraints
1. **Internal service untouched**: `LegacyInternalXMLService` must continue producing XML untouched.
2. **Order Sensitivity**: The client-facing response must be valid for the order they specify (e.g., compress-then-encrypt vs. encrypt-then-compress).
3. **No Class Explosion**: Any combination of transformations must be supported without creating a separate class per combination.
4. **Future Extensibility**: Adding a third optional transform later (e.g., **digital signature**) must require **no changes** to existing transform classes or to the XML $\rightarrow$ JSON conversion logic.

---

## Design Pattern Analysis

### Patterns Applied: **Adapter Pattern + Decorator Pattern**

### Why Adapter?
- Converts the incompatible XML output from `LegacyInternalXMLService.getDataAsXML()` into the JSON contract expected by `ApiResponse.getBody()`.
- The adapter serves as the **Concrete Component** for the subsequent decorator chain!

### Why Decorator?
- **Avoids Class Explosion**: With $N$ optional orthogonal transforms, Decorator enables $2^N$ dynamic combinations using only $N$ decorator classes instead of $2^N$ subclasses.
- **Order-Preserving**: Wrapping order directly defines execution order (e.g. `new EncryptionDecorator(new CompressionDecorator(adapter))` compresses first, then encrypts).
- **Open/Closed Principle**: Adding `SignatureDecorator` required zero edits to `XmlToJsonAdapter`, `EncryptionDecorator`, or `CompressionDecorator`.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Adaptee** | `LegacyInternalXMLService` | Produces legacy XML. Cannot be modified. |
| **Target / Component** | `ApiResponse` | Target interface defining `String getBody()`. |
| **Adapter (Concrete Component)** | `XmlToJsonAdapter` | Implements `ApiResponse`, wraps `LegacyInternalXMLService`, parses XML to JSON. |
| **Decorator Base** | `ApiResponseDecorator` | Abstract class implementing `ApiResponse` and delegating to wrapped `ApiResponse`. |
| **Concrete Decorator 1** | `EncryptionDecorator` | Encrypts response body using Base64 encoding. |
| **Concrete Decorator 2** | `CompressionDecorator` | Compresses response body using GZIP + Base64 encoding. |
| **Concrete Decorator 3** | `SignatureDecorator` | Extensibility proof: appends SHA-256 digital signature to payload. |
| **Client** | `Main` | Simulates HTTP requests with dynamic transformation flags. |

---

## Class Architecture

```
LegacyInternalXMLService (Adaptee)
  +getDataAsXML(): String
         ^
         | wraps
  XmlToJsonAdapter (Adapter / Concrete Component)  implements  ApiResponse (Target)
  +getBody(): String (JSON format)                               +getBody(): String
         ^                                                              ^
         | wrapped by                                                   | implements
  ApiResponseDecorator (Abstract Decorator) ----------------------------+
  -wrappee: ApiResponse
         ^
         +------------------------+------------------------+
         |                        |                        |
EncryptionDecorator      CompressionDecorator      SignatureDecorator
([ENCRYPTED: ...])       ([GZIP_COMPRESSED: ...])  ([SIGNATURE_SHA256: ...])
```

---

## Solution Walkthrough

1. **Adapter (`XmlToJsonAdapter.java`)**:
   Parses `<user><id>101</id><name>Ishrat</name></user>` into `{"id": "101", "name": "Ishrat"}`.
2. **Decorator Hierarchy**:
   ```java
   public abstract class ApiResponseDecorator implements ApiResponse {
       protected final ApiResponse wrappee;
       public ApiResponseDecorator(ApiResponse wrappee) { this.wrappee = wrappee; }
       @Override public String getBody() { return wrappee.getBody(); }
   }
   ```
3. **Dynamic Decorator Stacking by Request Parameters**:
   ```java
   ApiResponse response = new XmlToJsonAdapter(new LegacyInternalXMLService());
   if (compress) response = new CompressionDecorator(response);
   if (encrypt) response = new EncryptionDecorator(response);
   if (sign) response = new SignatureDecorator(response);
   ```

---

## How to Compile & Run

```bash
cd Batch_23/C1_ApiResponseAdapterDecorator
javac *.java
java Main
```

### Verified Output
```
[1] Internal Legacy Service (Raw XML):
    <user><id>101</id><name>Ishrat</name></user>

[2] Client Response via Adapter (Pure JSON, neither encrypted nor compressed):
    {"id": "101", "name": "Ishrat"}

[3] Client Response (?encrypt=true):
    [ENCRYPTED: eyJpZCI6ICIxMDEiLCAibmFtZSI6ICJJc2hyYXQifQ==]

[4] Client Response (?compress=true):
    [GZIP_COMPRESSED: H4sIAAAAAAAA/6tWykxRslJQMjQwVNJRUMpLzE0FcT2LM4oSS5RqAZmXkVgfAAAA]

[5] Client Response (Compress -> Encrypt):
    [ENCRYPTED: W0daSVBfQ09NUFJFU1NFRDogSDRzSUFBQUFBQUFBLzZ0V3lreFJzbEpRTWpRd1ZOSlJVTXBMekUwRmNUMkxNNG9TUzVScUFabVhrVmdmQUFBQV0=]

[6] Client Response (Encrypt -> Compress):
    [GZIP_COMPRESSED: H4sIAAAAAAAA/4t29XMOigwIcXWxUkit9CqIcvY083T2rPB1cc30cXbMTMp1K4kKBol5eSUbZVRGRgRmpgXa2sYCAAhzU4I5AAAA]

[7] Dynamic Request: ?compress=true&encrypt=true&sign=true
    Result: [ENCRYPTED: W0daSVBfQ09NUFJFU1NFRDogSDRzSUFBQUFBQUFBLzZ0V3lreFJzbEpRTWpRd1ZOSlJVTXBMekUwRmNUMkxNNG9TUzVScUFabVhrVmdmQUFBQV0=] [SIGNATURE_SHA256: 0a43efe9d55d4a16...]
```
