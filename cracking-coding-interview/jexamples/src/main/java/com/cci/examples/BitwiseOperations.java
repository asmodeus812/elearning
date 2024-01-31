package com.cci.examples;

public class BitwiseOperations {

    public static final class BitwiseOpsActions {

        public int toggleBit(int value, int bit) {
            // to toggle a bit we first create a mask with the specific bit set to 1, the rest are going to be 0s. To toggle the bit we can
            // simply xor the mask and the original value. What happens, is that the 0s in the mask would keep the 1s from the value, since
            // and the 0s from the mask would keep the 0s from the original value too. Since XOR (1 ^ 0 = 1) and XOR (0 ^ 0 = 0)
            int mask = (1 << bit);
            return value ^ mask;
        }

        public int clearBit(int value, int bit) {
            // to clear a bit, what we need to consider is that the specific bit should become 0, while the reset must remain as they are,
            // to do this at the bit position we have to have a 0 value, and the rest of the mask should be all 1s, then we and the value
            // and the mask - bit(2) -> value (10110) & mask(11011) = result(10010). Note that the value of the bit is an index, bit(2)
            // means the 3rd bit in the number
            int mask = ~(1 << bit);
            return value & mask;
        }

        public int setBit(int value, int bit) {
            // to set a bit, whata we need to consider is that the specific bit should become 1, whle the rest must remain as they are, to
            // do this at the bit position we have to have a 1, and the rest of the bits can be 0, but instead of AND, we do a bit wise or
            // between the mask and the value - bit(3) -> value (10110) | mask(01000) = result(11110). Note that the value of the bit is an
            // index, bit(3) means the 4th bit in the number
            int mask = (1 << bit);
            return value | mask;
        }

        public int getBit(int value, int bit) {
            // to get the value of a bit, we need to consider what that entails, to first shift the bit we want to the begining of the
            // bit wise order, meaning we shift to the right the original value exactly 'bits' number of bits, then we can bit wise and this
            // value with the value of 1, the value of 1 has just one bit set, the 0th bit, the rest are all 0s, AND it with the shifted
            // number would leave only that 0th bit - bit(3) -> value(10110) >> 3 -> shifted(00010) & mask(00001) -> result(00000). Note
            // that the value of the bit is an index, bit(3), means the 4th bit in the number
            int shifted = value >> bit;
            return shifted & 0x01;
        }
    }

    /**
     * Given two 32 bit numbers N and M and two bit positions i and j. Write a method to insert M into N such that M start at bit j and ends
     * at bit i.
     *
     * In the given task it is assumed that the range of i and j would always be valid for the target number N. What we want to do here is
     * create two masks called head and tail, then we combine them such that the space in the middle from i to j would be only 0s and the
     * other bits outside that range would be only 1s. Then we bit wise and source and that combined mask, this would zero out exactly the
     * bits in the source where the value has to be inserted. The final step is to bit wise or the value into that space of 0s, to place it
     * there. two masks
     *
     */
    public static final class InsertNumberBitwise {

        public int insertNumberBitwise(int source, int value, int j, int i) {
            // create the head mask, this mask will contain only 1s from bit j + 1 forwards, and 0s for the bits before that. How we do this
            // is by getting the number -1, which in twos complement bit wise representation is only 1s, then we shift left all those ones
            // by
            // j + 1
            int head = ((-1) << (j + 1));

            // crate the tail mask, this mask will contain only 1s from bit i to bit 0, backwards, and 0s for the bits after i. How we do
            // this is by getting again the number -1, which is remember again only 1s, we shift left by i, then we flip the bits, so the 1s
            // become 0s and vise versa
            int tail = ~((-1) << (i + 0));

            // here we combine the two masks, where the result of this bit wise or would be only 0s in the window of bits from i to j, the
            // rest of the bits after j and before i are going to be only 1s
            int combine = head | tail;

            // bit wise and the source value, this would clear all bits in the source value in the window from i to j, the rest are going to
            // remain the same
            source = source & combine;

            // shift the value left by i, to fit into the window of i to j, and then bit wise or it against the masked source that would
            // move
            // the bits of the value placed exactly in the bits window i to j
            value = (value << (i + 0));
            return source | value;
        }
    }

    public static final class FlipBitOperation {

        public int flipBitNumber(int value) {
            BitwiseOpsActions actions = new BitwiseOpsActions();

            // some basic state variables to track the max sequence, the current sequence, the first of the first 0 that we flipped, and if
            // we have flipped, we can instead of keping the flip flag, use the flip index as negative or positive number as a flag
            int max = 0;
            int seq = 0;

            int flipidx = 0;
            boolean flipflag = false;

            int i = 0;
            // loop though the allowed range of bits, for 4byte number that would be 32 bits, the bits are indexed from 0 to 31, since the
            // max number a 4byte integer can hold is 2^32 - 1 (a number with bits that are all 1s)
            while (i < 32) {
                // extract the current bit from the target value
                int bit = actions.getBit(value, i);

                if (bit == 1) {
                    // when the bit is 1, we simply continue with the sequence, just increase it
                    seq++;
                } else {
                    // when the bit is 0, we have two options, we have already fliped a bit or not
                    if (!flipflag) {
                        // when we have not flipped a bit we continue to increase the sequence, but we also remember the current position of
                        // the 0th bit, later on this is the position from where we are going to start checking for the next sequence
                        flipflag = true;
                        flipidx = i;
                        seq++;
                    } else {
                        // when we have already flipped and have met a new 0 bit, that means that we have to terminate checking, however the
                        // new index i must be set to the flipidx + 1 index, that is the bit after the first 0th bit we encountered, why ?
                        // well setting the i to everything smaller or equal to flipidx index, would only produce shorter sequences, since
                        // we will always traverse over that first 0 bit, and keep flipping it, while our i increases, the position of that
                        // 0th bit does not move, meaning we find the max seq only the very first time we go through that first 0 we can
                        // flip, every other iteration would increase i and produce only shorter sequences thorugh that 0th bit, we can
                        // start from the bit after it, which is why we set the i to be flipindex + 1
                        i = flipidx + 1;
                        flipflag = false;
                        flipidx = 0;
                        seq = 0;

                        // note the continue here, we do not need to compare seq with max, since we do not increase seq in this else branch,
                        // if seq was bigger than max in last iteration, it would have been assigned to max, here we only take care of
                        // reseting the state, as we know we have encountered a second 0, after we have already flipped a 0 once, the seq
                        // counting has to start from flipindex + 1, and the seq has to be rest to 0
                        continue;
                    }
                }

                // each time check the sequence against the current max seq, and update the max if needed
                if (seq > max) {
                    max = seq;
                }
                // move i incrementatlly, this can happen only in two cases - the current bit was 1 or we have flipped a 0 bit for the first
                // time
                i++;
            }
            return max;
        }
    }

    public static final class ComputeNextNumber {

        // public int getNextNumber(int n) {}
    }

    /**
     * Explain what the following implementation does, with the number n and what result it gives us
     *
     * The implementation below checks if a given positive integer is a power of two, this is done by leveraging the representation of a
     * power of 2 number, in binary, for which we know there is only 1 singular bit that is 1, and it's position is simply randing from bits
     * 0 to bit 31.
     */
    public static final class DebuggerCodeFlow {

        public boolean checkPowerTwo(int n) {
            // this is a pretty commonly used approach to check if a number if a power of 2, how does it work, think about how a power of
            // two number is represented, it has only one 1 bit set in its representation, for example the number 32, which is 2^5, which
            // looks like: 0b100000 <> 1 << 5. If we subtract 1 from that number we would get 0b011111 <> 31. If we bit wise AND those two
            // numbers, we will get exactly and always 0. Any other number that is not a power of two would not produce a 0 when we bit wise
            // and them with the same (number - 1)
            return (n & (n - 1)) == 0;
        }
    }

    /**
     * Write a function that counts how many bits we have to flip to turn number 'a' into number 'b'
     *
     * This is somewhat straight forward, we have to find the bits in each position from 0 to 31, where there are differences, meaning 0 or
     * 1 in number 'a' against a 1 or a 0 in number 'b'. This is done by XORing the two numbers to produce a mask where 1s would be placed
     * exactly at the bit positions where there is a difference
     *
     */
    public static final class CountFlipBits {

        private static final BitwiseOpsActions ACTIONS = new BitwiseOpsActions();

        public int countBitFlips(int a, int b) {
            // this is the most important part here, to realize that XORing the two numbers would give us a mask where only the bits which
            // were different in each position would be 1s, the rest which were equal are going to be 0s. Then we simply count how many 1s
            // we have in the mask
            int counter = 0;
            int mask = a ^ b;

            // go through the total number of bits in the given mask number
            for (int i = 0; i < 32; i++) {
                // get each bit and check if it is 1, after applying the xor action on a and b
                if (ACTIONS.getBit(mask, i) == 1) {
                    // when we encounter a 1, that means after the XOR bitiwse operation, in that position the two bits from a and b were
                    // different, therefore if they are different, there is 'flip' to be done, so increment the flip counter
                    counter++;
                }
            }
            return counter;
        }
    }


    /**
     * Write a function that swaps the odd and even bits in a number with as few operations as possible.
     *
     * What can be done here, is to consider first how to swap the bits, then how to mask off the bits we do not care about, and combine the
     * result. To swap the bits we just do two shifts, one to the left, one to the right, then we mask off the bits we do not care about
     * from the shifted results, and combine the shifted results with bit wise or
     *
     */
    public static final class PairwiseSwapBits {

        public int pairBitSwap(int n) {
            // first create two mask templates, which represent only the odd and the even bits in a number, this can be caclulated but it is
            // expliclity written to demonstrate what the two (even/odd) masks look like. Below they are used to expliclity extract only the
            // odd or even bits from the shifted numbers.
            int evenMask = 0b10101010101010101010101010101010;
            int oddMask = 0b01010101010101010101010101010101;

            // shift the numbe to the left, that would move the odd bits into the even positions, if we think about the number 0b11010 << 1
            // = 0b10100. Note how the odd bits moved into the positions of the even ones.
            int evenMove = n << 1;

            // shift the number to the right, that would move the even bits into the odd positions, if we think about the number 0b11010 >>
            // 1 = 0b01101. Note how the even bits moved into the positions of the odd ones.
            int oddMove = n >> 1;

            // now we mask off away with 0s the even bits from the oddMove, and the odd ones with 1s, to leave only the odd bits
            int oddBits = oddMove & oddMask;

            // now we mask off away with 0s the odd bits from the evenMove, and the even ones with 1s, to leave only the even bits
            int evenBits = evenMove & evenMask;

            // combine the two masks
            return evenBits | oddBits;
        }
    }

    public static final class DrawStraightLine {

        public void drawHorizontalLine(byte[] screen, int w, int x1, int x2, int y) {
            // claculate the width in number of bytes on the screen, that is how many bytes make the width of the screen, per row, w is in
            // pixel space, while width is in bytes space. The width along with the y position is used to offset into the one dimentional
            // screen array such that we know where / at which byte we must start
            int width = w / 8;

            // calculate the height in number of pixels. that is how many pixels horizontally the screen spans, this is simply the length of
            // the screen array (which is in bytes space) divided by the number of rows (width, also in bytes space), the result is the
            // number of rows, but each row technically represents 1 pixel too
            int height = screen.length / width;

            // to calculate the starting index we simply indeger divide the x1 by the width
            int start = x1 / 8;

            // the left over, or the mod here tells us how many bits are set, the mod result has to be greater than 0, remember that the x1
            // and x2 are positions not in
            int head = x1 % 8;

            int end = x2 / 8;
            int tail = x2 % 8;

            // calculate an offset into the screen array, tells us where our target row / byte begins, the one with coordinates of x1, we
            // also know that x2 is on the same row, since we are trying to draw a horizontal line, x1 and x2 have to be on the same row
            int yoff = y * width;

            // in the inclusive range [start, end] first fill up the bytes with 1s, later on we are going to grab the two bytes at the start
            // and end, and trim them. Such that the [start] byte is going to have 0s betwen bits [8, x1], and the [end] byte is going to
            // have 0s between bits [x2, 0].
            for (int i = start; i <= end; i++) {
                // fill the range with 1s = 255
                screen[yoff + i] = (byte) 0xFF;
            }

            if (head != 0) {
                // the mod result, is going to be a number between 0 and 7, 
                int mask = ((1 << (8 - (head - 1))) - 1);
                screen[yoff + start] &= mask;
            }

            if (tail != 0) {
                int mask = ~((1 << (8 - (tail - 1))) - 1);
                screen[yoff + end] &= mask;
            }

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    byte curr = screen[(i * width) + j];
                    String string = Integer.toBinaryString(curr);
                    string = String.format("%32s", string).replace(' ', '0');
                    System.out.print(string.substring(32 - 8));
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        FlipBitOperation flip = new FlipBitOperation();
        flip.flipBitNumber(0b11011101111);

        PairwiseSwapBits pair = new PairwiseSwapBits();
        boolean f = pair.pairBitSwap(0b101010) == 0b010101;
        f = pair.pairBitSwap(0b010101) == 0b101010;
        DrawStraightLine draw = new DrawStraightLine();
        byte[] screen = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        draw.drawHorizontalLine(screen, 16, 3, 12, 1);

        return;
    }
}
