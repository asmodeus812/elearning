package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesAndPath {

    private static final int calculatePathDepth(Path path) {
        if (path.getParent() == null) {
            return 0;
        }
        return 1 + calculatePathDepth(path.getParent());
    }

    /**
     * A generic file visitor that ensures that we are going to visit only files or folders no deeper than 2 levels for directories, and no
     * deeper than 3 levels for files, this ensures that we only visit the top level of the home directory of the user under /home/user/*,
     * and all files and folders immediate to this level of directories. The walk method in the Files utilty class has an overload to handle
     * depth, but we demonstrate that we can handle this ourselves, our implementation is only handling aboslute path depth, not relative
     * depth
     */
    private static final class FileVisitorImplementation implements FileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            LOGGER.logInfo(String.format("Visited directory %s", dir));
            return calculatePathDepth(dir) >= 3 ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            LOGGER.logInfo(String.format("Visited file %s", file));
            return calculatePathDepth(file) > 3 ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            if (!Objects.isNull(exc)) {
                LOGGER.logSevere(exc);
                throw exc;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (!Objects.isNull(exc)) {
                LOGGER.logSevere(exc);
                throw exc;
            }
            return FileVisitResult.CONTINUE;
        }
    }

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(FilesAndPath.class);

    // note that we have made this main method throw an exception, that is because a lot of the methods from Files throw, as expected,
    // checked exceptions, and will make the examples easier to read, if we have no try/catch blocks
    public static void main(String[] args) throws IOException {
        // ensure that the logger is first configured to print abridged messages we do this to ensure that the logger format string does not
        // pollute the stdout with needless noise when printing to stdout.
        InstanceMessageLogger.configureLogger(FilesAndPath.class.getResourceAsStream("/logger.properties"));

        // the most basic example of reading a file, we resolve the path of the file, keep in mind that these methods will throw, in case
        // the file is not found, and/or the path is actually invalid, or incomplete, like the path in this case points to a file that is
        // located in a location that is not known to the execution enviornment, the properties file is going to be put into the jar, in a
        // specific location along side the class files, therefore reading the file will not be successful, therefore this is not a good
        // idea - Path fileLocationPath = Path.of("logger.propeties")

        // the resource is put into the src/main/resources, when building the project those files will be put into target/classes, and if we
        // construct a jar those will go into the jar as well, however the full path of this resource is still required, we use the
        // getResource here tohelp us locate the file, using the class and its default class loader
        URL fileLocationPathUrl = FilesAndPath.class.getResource("/logger.properties");
        Path fileLocationPathOnly = Path.of(fileLocationPathUrl.getPath());
        // the actual file location here will point to something like /home/<user>/<project>/learning/target/classes/logger.properties, this
        // is actually what is expected the full path name instead of the partial path name, which is what the Path.of("logger.properties")
        // would give us. Generally it is adivsed to used absolute paths where possible that way we can eliminate ambigious paths and ensure
        // that we are refering to the correct resource.
        String plainStringContent = Files.readString(fileLocationPathOnly);

        // Keep in mind that reading this string will keep the new lines in the file, so if that string is printed out to a terminal these
        // new lines control characters will not show up as \n, but will instead be interpreted by the terminal as new line control
        // characters, and actually create new lines, when printing this content, this behavior is different than what we will get with
        // readAllLines, which strips the new line control characters and instead inserts every new line as a new line/string into the final
        // result of the method
        LOGGER.logInfo("readString");
        LOGGER.logInfo(plainStringContent);

        // this method is an extension of the one above, instead of interpreting the file as one big string, we are ensuring that new line
        // characters are correctly parsed into new entries into the list, every line will be a new string object in this list, and the
        // actual new line bytes will not be present in any of the final strings in this list, unlike the readString, which will contain all
        // control sequences including the new lines
        List<String> plainFileLines = Files.readAllLines(fileLocationPathOnly);
        LOGGER.logInfo("readAllLines");
        LOGGER.logInfo(plainFileLines);

        // this method is a bit different, even though it might be just simply looking like the one above, just more cumbersome, that is not
        // true, the readString reads the file as if it were a text file meaning that the encoding of the file directly affects what result
        // we will get, while readAllBytes, reads bytes, that is it, it does not concern itself with how the file was encoded or even if it
        // is a text file, which is why it is mostly used to read binary file formats, or non-text formats. We still wrap it into a String
        // constructor to allow us to print it, by default that String constructor will interpret the content of the bytes as UTF-8 sequence
        byte[] bytes = Files.readAllBytes(fileLocationPathOnly);
        LOGGER.logInfo("readAllBytes");
        LOGGER.logInfo(new String(bytes));

        // on each system there are a set of shared common file system attributes, that all file system regardless of the system share, like
        // owner, read/write permissions etc.
        var basicFileAttributes = Files.readAttributes(fileLocationPathOnly, BasicFileAttributes.class);
        LOGGER.logInfo(String.format("Basic file attributes %s", basicFileAttributes));
        LOGGER.logInfo(String.format("isDirectory: %s", basicFileAttributes.isDirectory()));
        LOGGER.logInfo(String.format("isSymbolicLink: %s", basicFileAttributes.isSymbolicLink()));
        LOGGER.logInfo(String.format("isRegularFile: %s", basicFileAttributes.isRegularFile()));
        LOGGER.logInfo(String.format("lastAccessTime: %s", basicFileAttributes.lastAccessTime()));
        LOGGER.logInfo(String.format("lastModifiedTime: %s", basicFileAttributes.lastModifiedTime()));
        LOGGER.logInfo(String.format("creationTime: %s", basicFileAttributes.creationTime()));

        // the posix permissions are the only ones that have some sort of agreed on specification and the java virtual environment is
        // implementing them, for other operating system specific ones we can use different method.
        var specificFileSystemAttributes = Files.readAttributes(fileLocationPathOnly, PosixFileAttributes.class);
        LOGGER.logInfo(String.format("POSIX file attributes %s", specificFileSystemAttributes));
        LOGGER.logInfo(String.format("owner: %s", specificFileSystemAttributes.owner()));
        LOGGER.logInfo(String.format("group: %s", specificFileSystemAttributes.group()));
        LOGGER.logInfo(String.format("permissions: %s", specificFileSystemAttributes.permissions()));

        // we take the root or parent folder of the file location where the logger properties are and we will append multiple nodes to this
        // directory to create a new directory path under target/...
        Path directoryLocation = fileLocationPathOnly.getParent();
        // ensure that this can never happen, the path we have should always have a parent it is not located at the root of the file system,
        // most likely, but the method will throw an exception if the path we had had no root location, therefore we ensure that there is
        // a parent to be extracted for this path.
        assert (directoryLocation != null);
        directoryLocation = directoryLocation.resolve("child1").resolve("child2").resolve("child3");

        // create the entire chain of the directories for the new path the resolve chains above will create a new path with all of those new
        // nodes attached to the parent, and return a new path object, remember though that Path does not validate if the path represented
        // by it actually exists, that will be done by the methods that operate with thes Path objects
        Files.createDirectories(directoryLocation);
        LOGGER.logInfo(String.format("Created a new directory %s", directoryLocation));
        LOGGER.logInfo(String.format("Root of the new directory is %s", directoryLocation.getRoot()));

        // method to help us create a temporary directory, into a given location, that is useful if we wish to create a unique directory
        // that we will dispose of eventually in short time, the directory is created with a given prefix, into a given location
        Path temporaryNewDirectory = Files.createTempDirectory(directoryLocation, "temp-dir-");
        LOGGER.logInfo(String.format("Created temporary directory at %s", temporaryNewDirectory));

        // on top of temporary directories we can also create temporary files, these are also ensured to be unique, and can be provided with
        // a suffix also on top of the prefix, we create this new temp file into the temp directory that we created above.
        Path temporaryNewFile = Files.createTempFile(temporaryNewDirectory, "prefix-", "-suffix");
        LOGGER.logInfo(String.format("Created temporary file at %s", temporaryNewFile));

        // below we are going to make a few writes to the files we have just created but before that we have to ensure that they exist, here
        // we are doing just that, for both the temporary file and directory we ensure that we have created the correct structure, and the
        // actual paths exist
        LOGGER.logInfo(String.format("Checking if the temporary directory exists: %s",
                        Files.exists(temporaryNewDirectory) ? "true" : "false"));
        LOGGER.logInfo(String.format("Checking if temporary file exists: %s", Files.exists(temporaryNewFile) ? "true" : "false"));

        // write out the content of the logger.properties file back into the temporary file, then we will check what is the size
        LOGGER.logInfo("Writing string content to the temporary file");
        LOGGER.logInfo(String.format("Checking size of the temporary file before writing to it %d", Files.size(temporaryNewFile)));
        for (int i = 0; i < 100; i++) {
            // writing out the contents of the logger properties file a hundred times and then we will verify that the files size of the
            // temporary file is not zero, note the usage of the append open option, here we are using that one to ensure that every call
            // to the file happens at the end, appending to the end of it, instead of deleting existing content
            Files.writeString(temporaryNewFile, plainStringContent, StandardOpenOption.APPEND);
        }
        // checkout the size of the file after we write to it, we have to have written some content to the file at this point, and the size
        // should be considerably bigger than 0
        LOGGER.logInfo(String.format("Checking size of the temporary file after write %d", Files.size(temporaryNewFile)));

        // hidden folder and directories are those that contain a literal dot in their names, however the actual hidden part is only denoted
        // by the leaf element, in the path, therefore if the leaf does not contain dot in the path the path is not considered hidden even
        // if it is located into a hidden folder location like the example below here
        Path hiddenFolderDirectoryPath = Path.of("/home", "user", ".config");
        LOGGER.logInfo(String.format("The target %s path is %s", hiddenFolderDirectoryPath,
                        Files.isHidden(hiddenFolderDirectoryPath) ? "hidden" : "not-hidden"));
        hiddenFolderDirectoryPath = Path.of("/home", "user", ".config", "not-hidden-folder");
        LOGGER.logInfo(String.format("The target %s path is %s", hiddenFolderDirectoryPath,
                        Files.isHidden(hiddenFolderDirectoryPath) ? "hidden" : "not-hidden"));

        // this is effectively the same as the manual extraction of the permissions/file attributes that we have done above, however it only
        // pulls the data for the owner, the same result is however used in this case the interface called UserPrincipal.
        UserPrincipal owner = Files.getOwner(temporaryNewFile);
        LOGGER.logInfo(String.format("The owner of %s, is %s", temporaryNewFile, owner));

        // it is generally a good idea to verify if certain paths exist before we use them in most methods within the Files.* family,
        // otherwise these methods might throw an exception, in case those paths do not exist
        boolean existsHiddenDirectoryFilePath = Files.exists(hiddenFolderDirectoryPath);
        LOGGER.logInfo(String.format("The path %s, %s", hiddenFolderDirectoryPath,
                        existsHiddenDirectoryFilePath ? "exists" : "does not exist"));

        // since those resources do not exist, we can use methods such as ifExists, however only the delete method has this variation, in
        // this case the delete method call here will return false, the path does not exist therefore the status will be false, the delete
        // operation did not succeed. Also the deleteIfExists will throw in case the resources exists but can not be deleted, due to
        // permissions etc.
        boolean deletedResultForNonExistingPath = Files.deleteIfExists(hiddenFolderDirectoryPath);
        LOGGER.logInfo(String.format("The path %s, %s", hiddenFolderDirectoryPath,
                        deletedResultForNonExistingPath ? "was deleted" : "was not deleted"));

        // okay this needs more attention, here, this call will fail, because we create the temporary directory, actually we can not do this
        // by default the copy operation does not like when resources exist, it does not do overrides by default
        Path temporaryNewDirectory2 = Files.createTempDirectory(directoryLocation, "temp-dir-2-");
        LOGGER.logInfo(String.format("Created temporary directory at %s", temporaryNewDirectory));
        try {
            // why does this fail, well the temporary directory was already created, so copy by default does not override the location,
            // additional copy attributes have to be provided to actually tell the java environment to override this file and path,
            Files.copy(temporaryNewDirectory, temporaryNewDirectory2);
        } catch (Exception e) {
            LOGGER.logSevere(e);
        }
        // this will be fine, because we tell the copy operation, to actually replace any existing paths and resources, that is destructive,
        // since it does not merge, but completely overrides the target destination
        Path resultOfCopyOperation = Files.copy(temporaryNewDirectory, temporaryNewDirectory2, StandardCopyOption.REPLACE_EXISTING);
        LOGGER.logInfo(String.format("The copy-replace existing operation completed for %s", resultOfCopyOperation));

        // this is more involved operation, here we create a long path that does not exist starting off of temporaryNewDirectory2, in this
        // case we also have to create at least the full path down to the very parent of the leaf directory in this case the path one/two,
        // the folder three has to be left off un-created so that the copy operation will actually create it.
        Path temporaryButNonExistentLongPath = temporaryNewDirectory2.getParent().resolve("one").resolve("two").resolve("three");
        Files.createDirectories(temporaryButNonExistentLongPath.getParent());
        // we copy the contents of the temporaryNewDirectory, into the new temporaryButNonExistentLongPath, this one will ensure that the
        // last leaf/component file path element of the path is created and we have left it uncreated intentionally
        resultOfCopyOperation = Files.copy(temporaryNewDirectory, temporaryButNonExistentLongPath);
        LOGGER.logInfo(String.format("Copied src %s into target %s", temporaryNewDirectory.getFileName(),
                        resultOfCopyOperation.getFileName()));

        // the move action works the exact same way as does copy, it does not override locations by default, so we are not going to
        // investigate the different use csaes, we are going to directly use a valid move action, we are going to create a new sub-folder in
        Path temporaryButNonExistentLongPath2 = temporaryNewDirectory2.getParent().resolve("one").resolve("two").resolve("four");
        Files.createDirectories(temporaryButNonExistentLongPath2.getParent());
        resultOfCopyOperation =
                        Files.move(temporaryButNonExistentLongPath, temporaryButNonExistentLongPath2, StandardCopyOption.REPLACE_EXISTING);
        LOGGER.logInfo(String.format("Moved src %s into target %s", temporaryButNonExistentLongPath, resultOfCopyOperation));

        // we would like to now walk over all non hidden directories in the home folder of the user, these are all directories or paths that
        // do not contain any literal dot in their path
        String homeDirectoryForCurrentUser = System.getProperty("user.home");
        Path homePathDirectory = Path.of(homeDirectoryForCurrentUser);
        if (Files.exists(homePathDirectory)) {
            LOGGER.logInfo(String.format("User directory not accessible or does not exist %s", homePathDirectory));
        } else {
            LOGGER.logInfo(String.format("Visiting the user home directory at %s", homePathDirectory));
        }
        // we are walking the directories using a very specific implementation of the file visitor, that visitor ensures that we do not walk
        // the entire directory tree, which might be too big, for the users home folder, therefore this visitor ensures the depth is not
        // overshot, too much when walking the tree.
        Path resultOfWalker = Files.walkFileTree(homePathDirectory, new FileVisitorImplementation());
        LOGGER.logInfo(String.format("Result of the walking method is %s", resultOfWalker));

        // the simple walker produces a temporary streamthat stream is actually internally implemented with anonymous structure that
        // structure is holding the io resources therefore when the stream is closed with a terminal operation the resources are going to be
        // closed and freed along with the stream, however it is a good practice to ensure the closing is guaranteed with try-with-resources
        // wiht multiple calls to close method are not going to cause exception so we can ensure that the stream is clsoed always outside of
        // the try block even if we do not call any terminal operation on the stream
        try (Stream<Path> simpleWalkedPaths = Files.walk(temporaryNewDirectory2.getParent(), 5, FileVisitOption.FOLLOW_LINKS)) {
            LOGGER.logInfo(String.format("Simple walker Visited paths were %n%s",
                            simpleWalkedPaths.map(Path::toString).collect(Collectors.joining("\n"))));
            // remember that the collect is a terminal operation, so by the time collect is called the internal io resources should have
            // been released, because the stream interface defines that calling any terminal operation on a stream will ensure the stream
            // enters in a closed state
        }

        // just like the resources above related to the walker we need to ensure that the stream is clsoed, the auto closeable interface is
        // part of the the Closeable interface
        try (DirectoryStream<Path> newDirectoryStreamFromHomeDir = Files.newDirectoryStream(homePathDirectory)) {
            LOGGER.logInfo("Stream interator for home directory content is");
            newDirectoryStreamFromHomeDir.forEach(path -> {
                // just print out all of the directories, of the directory stream
                LOGGER.logInfo(String.format("Directory stream path %s", path));
            });
        }

        // we open a file channel to the logging properties file, the idea is to demonstrate, how the new channels api can be used to
        // supersede the existing file write/read interface that already exists in the old io API. This is mostly important when we want to
        // stream data from one channel to another, if the goal is to read the data into the java runtime, channels offer minimal
        // improvements over the existing basic standard old IO API
        try (FileChannel fileChannelToLoggingPath = FileChannel.open(fileLocationPathOnly)) {
            // the allocate direct here is important, what it does is allow us to zero-copy overhead data into the file by leveraging some
            // of the kernel system calls directly, usually using the traditional old API might incur a lot of overhead due to copying
            // buffer data all around, while the new nio.2 channels are more robust and performant
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            // this buffer here is to allow us to bring this data buffer into the java runtime, remember that byte buffers and especially
            // the direct byte buffer is optimized to do transfer point to point copy operations, however, these often do not end up in the
            // java runtime, if we want to pull that data into the java runtime we have to copy the data into a byte buffer represented
            // structure. The ByteBuffer construct very likely keeps the data on the OS kernel level.
            byte[] temp = new byte[1024];
            LOGGER.logInfo("Reading the file contents from a FileChannel");
            while (fileChannelToLoggingPath.read(byteBuffer) != -1) {
                // it is important to flip the buffer every time we try to read from it, every write will offset the internal pointer of the
                // buffer forward, every time we want to read from it we have to restore the pointer. Think of it as putting the buffer into
                // a read mode after a write was done to it.
                byteBuffer.flip();
                // wrap the contents of the buffer into a string, that would ensure that we handle the encoding correctly, since we know
                // that this content is coming from a text file that has proper utf-8 encoding.
                LOGGER.logInfo(String.format("Buffer details after read %s", byteBuffer));
                // what we do here is pull the data from the ByteBuffer, from the kernel level, directly into the java runtime byte buffer
                // representation, that is a normal copy operation, usually we wont do this, because the channels are used to move data
                // from one channel or multiple channels to another channel, not to read data into the java runtime, otherwise just use the
                // regular io, the overhead is the same in that scenario
                byteBuffer.get(temp, 0, byteBuffer.remaining());
                // the string constructor here will ensure that we correctly interpret the bytes and their encoding, since the content is
                // coming form a text file, we need to take this into account if we wish to actually have the file contents correctly be
                // printed out / interpreted , as human readable content
                LOGGER.logInfo(new String(temp));
                // this is basically doing the inverse (semantically) of the flip it prepares the buffer for writing to it, meaning that it
                // prepares the buffer for writing, still reseting the position to 0. In reality the implementation of both flip and clear
                // are quite similar, with small differences.
                byteBuffer.clear();
            }
        }

        // here the key thing to take anote of is that we open both files in different modes, the file we are going to be reading from has
        // to be opened as such, and the more important one the one we are going to write to has to be opeend with the option to write.
        Path temporaryNewFile2 = Files.createTempFile(temporaryNewDirectory, "clone-", "-file");
        try (FileChannel fileChannelIn = FileChannel.open(temporaryNewFile, StandardOpenOption.READ);
                        FileChannel fileChannelOut = FileChannel.open(temporaryNewFile2, StandardOpenOption.WRITE)) {
            // this is demonstrating zero-copy overhead for the channel, this entire operation will happen in the kernel, ring 0, that is
            // the most efficient way to copy content form one file to another, java provies this improvement over the standard old API that
            // is the blocking Reader/Writer interface we know all too well, that interface and implementation forces the data to be copied
            // into user space then back into the kernel level when writing the data into the desitnation file, that has a huge overhead,
            // the same data gets copied at least 3 times going from kernel - user - kernel space. This is mostly the case for memory mapped
            // byte buffers.
            fileChannelIn.transferTo(0, fileChannelIn.size(), fileChannelOut);
            LOGGER.logInfo(String.format("Copied file from %s to %s", temporaryNewFile, temporaryNewFile2));
        }

        // another feature of channels is that they support scatter reading and gathered writing. What does it mean, the scatter read, is an
        // operation that allows us to read from one channel and scatter the result into multiple channels in a single readv sys call, the
        // gathered writing is the inverse of that, gather multiple channels and multiplex write them to a given channel, again using a
        // single writev system call
        Path temporaryNewFile3 = Files.createTempFile(temporaryNewDirectory, "experimental-", "-file");
        try (FileChannel fileChannelInOut = FileChannel.open(temporaryNewFile3, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            // okay so what we do here is to allocate two normal byte buffers, while these are operating in user space, they are still going
            // to require a copy to the kernel level eventually, they are still efficient
            ByteBuffer bufferOne = ByteBuffer.allocateDirect(1024);
            ByteBuffer bufferTwo = ByteBuffer.allocateDirect(1024);
            // ensure that they are prepared to be written to, this should be the case as we have just created them and no data was actually
            // written to these buffers, however it is a good practice to demonstrate the intent, what the clear method does really is
            // simply
            // set the position of the cursor to 0, and the capacity/limit of the buffer to the maximum size in this case 1KB = 1024 bytes
            bufferOne.clear();
            bufferTwo.clear();
            // put some random data into the buffers, so they are not empty, the data is not important, what is important here is that we
            // fill up the buffers, with something that is later going to be used to actually transfer the data into a single shot
            // writev(write vectorized) using our two buffers directly into the file
            for (int i = 0; i < 1024; i++) {
                bufferOne.put((byte) i);
                bufferTwo.put((byte) i);
            }
            // flip them back so we can read from these buffers that we just filled up, since we just put data in them we have to flip them
            // over, the flip resets the position, back to 0, and sets the buffer limit to however many bytes were actually written
            // originally, in this case they are full up
            bufferOne.flip();
            bufferTwo.flip();
            // write multiplex / scatter into the file channel, gather writing into the file, this is implemented using writev, meaning a
            // vectorized write, i.e multiplexing multilple buffer writes in a single shot system call
            fileChannelInOut.write(new ByteBuffer[] {bufferOne, bufferTwo});
            LOGGER.logInfo(String.format("Multiplexing buffer content into %s", temporaryNewFile3));
        }

        // the files interface provies a quick way to construct a seekable byte channel, which allows us to read a path contents as bytes,
        // this is quicker, more generic, and less involved than a straight FileChannel object and managing that. The inherent benefit of
        // the byte channel is that it is a more generic representation of a structure that can read bytes from a resource. The
        // SeekableByteChannel, is an interface that extends off of the ByteChannel, and provides means of positioning the pointer anywhere
        // in the resource
        try (SeekableByteChannel byteChannelForLoggingFileProperties =
                        Files.newByteChannel(fileLocationPathOnly, StandardOpenOption.READ)) {
            ByteBuffer directBufferDataHolder = ByteBuffer.allocateDirect(1024);
            LOGGER.logInfo(String.format("Reading data content of %s, as byte channel", fileLocationPathOnly));
            // offset the position of the channel with 64 bytes, into the file, using this method one has to be carefule because the offset
            // has to be less than the size of the files in bytes, otherwise the position will end up outside of the allowable range.
            byteChannelForLoggingFileProperties.position(64);
            // reading here will ensure that we actually read starting off 64 bytes forward into the resource, we can keep setting the
            // position to new values that will keep moving the active cursor at the absolute position that we set.
            while (byteChannelForLoggingFileProperties.read(directBufferDataHolder) != -1) {
                // prepare the byte buffer for reading
                directBufferDataHolder.flip();
                // read the contents into the user space
                byte[] temp = new byte[1024];
                int size = directBufferDataHolder.remaining();
                directBufferDataHolder.get(temp, 0, size);
                // print the contents of the buffer
                LOGGER.logInfo(new String(temp));
                // prepare the byte buffer to be written to
                directBufferDataHolder.clear();
            }
        }

        // here we can be cheeky, by allowing us to create a bridge between the new nio and channel api and the old io stream api, the new
        // api is going to be more robust and flexible, even though it is still blocking it provides more friendly interface and actually a
        // much richer interface for working with data from resources. In this example we pull an InputStream from a URL, that points to a
        // file on the local file system, because we are using the file:// schema
        URL fileLocationPathUrlTarget = new URL("file://" + fileLocationPathOnly.toAbsolutePath());
        try (ReadableByteChannel newUrlBackedInputStreamChannel = Channels.newChannel(fileLocationPathUrlTarget.openStream())) {
            LOGGER.logInfo(String.format("Opening stream to %s, based on URL file schema", fileLocationPathUrlTarget));
            ByteBuffer directBufferDataHolder = ByteBuffer.allocateDirect(1024);
            while (newUrlBackedInputStreamChannel.read(directBufferDataHolder) != -1) {
                // the following block is pretty much the same as we have done thus far, we initialize the direct buffer into read mode, it
                // was already written to by the read call in the while header, afte which we have to bring the data into the user space, by
                // copying it form the ByteBuffer, and we can then construct the string object from which we can directly log to the
                // terminal output
                directBufferDataHolder.flip();
                byte[] temp = new byte[1024];
                int size = directBufferDataHolder.remaining();
                directBufferDataHolder.get(temp, 0, size);
                LOGGER.logInfo(new String(temp));
                directBufferDataHolder.clear();
            }
        }

        SocketAddress socketAddressLocal = new InetSocketAddress("www.google.com", 80);
        try (SocketChannel selectableSocketChannelData = SocketChannel.open(socketAddressLocal)) {
            // selectableSocketChannelData.sel
        }

        // the few lines below demonstrate how we can work with absolute and relative paths we construct a relative path, but the absolute
        // path below has actually a very specific value, and not the absolute path to the file, in our case this file is under
        // /home/<user>/<project>/java-se-study/java-8/learning/src/main/java/com/learning/core/FilesAndPath.java, however, the
        // toAbsolutePath, will print something very very different, the result will most likely look something likethis -
        // /home/<user>/<project>/java-se-study/./FilesAndPath.java, that is because the absolute file path is calculated based on the
        // current working directory, where the application or more precisely the java virtual machine is started at, in our case that most
        // likely is the root of the project, and you will notice that this is not the actual location of the file, as we have already
        // mentioned the Path class has no notion of correctness, it simply only contains path representations. The normalize call will also
        // ensure that we do not get intermediate path nodes like /./ and collapse them into proper valid structure.
        Path relativeFileLocation = Paths.get("./FilesAndPath.java");
        LOGGER.logInfo(String.format("The relative path is %s", relativeFileLocation));
        LOGGER.logInfo(String.format("The absolute path is %s", relativeFileLocation.toAbsolutePath().normalize()));
    }
}
